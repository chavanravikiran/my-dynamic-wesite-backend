package com.webapp.websiteportal.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import com.webapp.websiteportal.entity.OnlinePayment;
import com.webapp.websiteportal.entity.OnlinePaymentReceipt;
import com.webapp.websiteportal.entity.PaymentStatus;
import com.webapp.websiteportal.entity.PaymentType;
import com.webapp.websiteportal.entity.RazorpayConfig;
import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.repository.AppointmentSlotRepository;
import com.webapp.websiteportal.repository.BookAppointmentRepository;
import com.webapp.websiteportal.repository.OnlinePaymentReceiptRepository;
import com.webapp.websiteportal.repository.OnlinePaymentRepository;
import com.webapp.websiteportal.repository.RazorpayConfigRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IRazorpayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RazorpayServiceImpl implements IRazorpayService{
	
	@Autowired
	private AppointmentSlotRepository slotRepo;
	
	@Autowired
    private BookAppointmentRepository bookRepo;
    
	@Autowired
	private WebsiteDetailsRepository webSiteDetailsRepository;
	
	@Autowired
	private RazorpayConfigRepository configRepo;
	
	@Autowired
	private OnlinePaymentRepository paymentRepo;
	
	@Autowired
	private OnlinePaymentReceiptRepository receiptRepo;
	
	@Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    @Override
    public Map<String, Object> createPayment(Long applicationId, BigDecimal amount,Users currentUser) throws Exception {
        RazorpayConfig config = configRepo.findByApiKeyAndApiSecretAndCurrencyAndIsActive(razorpayKeyId,razorpayKeySecret,"INR",'Y');
        if (config == null) {
            throw new RuntimeException("Razorpay configuration not found");
        }

        RazorpayClient client = new RazorpayClient(config.getApiKey(), config.getApiSecret());

        // Create OnlinePayment (Master)
        OnlinePayment payment = new OnlinePayment();
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentAmount(amount);
        payment.setPaymentType(PaymentType.ONLINE);
        payment.setPaymentStatus(PaymentStatus.CREATED);
        payment.setPaymentGateway("RAZORPAY");
        payment.setUserId(currentUser.getId());
        payment = paymentRepo.save(payment);

        // Create Razorpay order
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue()); // in paise
        orderRequest.put("currency", config.getCurrency());
        orderRequest.put("receipt", "receipt_" + payment.getKey());
        orderRequest.put("payment_capture", 1);

        Order order = client.orders.create(orderRequest);
        payment.setTransactionId(order.get("id"));
        payment.setRequestMessage(order.toString());
        paymentRepo.save(payment);

        Map<String, Object> map = new HashMap<>();
        map.put("razorpayOrderId", order.get("id"));
        map.put("razorpayKey", config.getApiKey());
        map.put("amount", amount);
        map.put("currency", config.getCurrency());
        map.put("companyName", config.getCompanyName());
        map.put("paymentId", payment.getKey());

        return map;
    }

    @Override
    public boolean verifyPayment(Map<String, String> payload) throws Exception {
        RazorpayConfig config = configRepo.findTopByOrderByKeyDesc();
        if (config == null) {
            throw new RuntimeException("Razorpay configuration not found");
        }

        String razorpayOrderId = payload.get("razorpayOrderId");
        String razorpayPaymentId = payload.get("razorpayPaymentId");
        String razorpaySignature = payload.get("razorpaySignature");
        Long paymentId = Long.valueOf(payload.get("paymentId"));

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", razorpayOrderId);
        options.put("razorpay_payment_id", razorpayPaymentId);
        options.put("razorpay_signature", razorpaySignature);

        boolean isValid = Utils.verifyPaymentSignature(options, config.getApiSecret());

        OnlinePayment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (isValid) {
            payment.setPaymentStatus(PaymentStatus.PAID);
            payment.setResponseMessage("Razorpay paymentId: " + razorpayPaymentId);
            paymentRepo.save(payment);

            // Create Receipt (Partial Payment Supported)
            OnlinePaymentReceipt receipt = OnlinePaymentReceipt.initObj(
                    payment,
                    payment.getPaymentAmount(),
                    "RAZORPAY",
                    razorpayPaymentId
            );
            receiptRepo.save(receipt);

            return true;
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            paymentRepo.save(payment);
            return false;
        }
    }
}
