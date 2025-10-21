package com.webapp.websiteportal.service;

import java.math.BigDecimal;
import java.util.Map;

import com.webapp.websiteportal.entity.Users;

public interface IRazorpayService {

	public Map<String, Object> createPayment(Long applicationId, BigDecimal amount,Users currentUser) throws Exception;
	
	public boolean verifyPayment(Map<String, String> payload) throws Exception;
}