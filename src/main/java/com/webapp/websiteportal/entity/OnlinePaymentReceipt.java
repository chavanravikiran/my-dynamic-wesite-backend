package com.webapp.websiteportal.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "online_payment_receipts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnlinePaymentReceipt extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "online_payments_seq", sequenceName = "online_payments_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "online_payments_seq")
	private Long key;
    
	private String razorpayPaymentId;
    private String razorpayOrderId;
    private String signature;
    private BigDecimal amount;
    private Date paymentDate;
    private String currency;
    private String paymentMethod;
//    private Instant createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "online_payment_id")
    private OnlinePayment onlinePayment;

	public static OnlinePaymentReceipt initObj(OnlinePayment onlinePayment, BigDecimal paidAmount, String paymentMethod,
			String razorpayPaymentId) {
		return OnlinePaymentReceipt.builder()
				.onlinePayment(onlinePayment)
				.amount(paidAmount)
				.paymentDate(new Date())
				.paymentMethod(paymentMethod)
				.razorpayPaymentId(razorpayPaymentId)
				.build();
	}   
}
