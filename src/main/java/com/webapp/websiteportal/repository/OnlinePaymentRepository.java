package com.webapp.websiteportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.OnlinePayment;

@Repository
public interface OnlinePaymentRepository extends JpaRepository<OnlinePayment, Long> {
   
	Optional<OnlinePayment> findByTransactionId(String transactionId);

	Optional<OnlinePayment> findByRazorpayOrderId(String orderId);
}
