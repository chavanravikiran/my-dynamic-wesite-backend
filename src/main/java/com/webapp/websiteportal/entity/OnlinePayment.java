package com.webapp.websiteportal.entity;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "online_payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnlinePayment extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "online_payments_seq", sequenceName = "online_payments_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "online_payments_seq")
	private Long key;
    
    @Column(nullable = false, unique = true)
    private String transactionId;

    private String razorpayOrderId;

    private Long amount;  // amount in paise
    private String currency;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    
    private  String paymentGateway;

    private Instant createdAt;
    private Instant updatedAt;
    
    private PaymentType paymentType;
    
    private String requestMessage;
    
    private String responseMessage;

    @OneToMany(mappedBy = "onlinePayment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OnlinePaymentReceipt> receipts = new ArrayList<>();

	private BigDecimal PaymentAmount;
}
