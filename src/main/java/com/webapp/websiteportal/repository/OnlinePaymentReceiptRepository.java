package com.webapp.websiteportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.websiteportal.entity.OnlinePaymentReceipt;

@Repository
public interface OnlinePaymentReceiptRepository extends JpaRepository<OnlinePaymentReceipt, Long> {
	
	
}
