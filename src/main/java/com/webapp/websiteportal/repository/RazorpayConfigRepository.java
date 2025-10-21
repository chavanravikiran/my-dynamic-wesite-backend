package com.webapp.websiteportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.RazorpayConfig;

@Repository
public interface RazorpayConfigRepository extends JpaRepository<RazorpayConfig, Long> {
	RazorpayConfig findTopByOrderByKeyDesc(); // always use latest config
	RazorpayConfig findByApiKeyAndApiSecretAndCurrencyAndIsActive(String razorpayKeyId, String razorpayKeySecret,String currency, Character isActive);
}
