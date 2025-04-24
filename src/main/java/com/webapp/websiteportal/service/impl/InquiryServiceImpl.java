package com.webapp.websiteportal.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.dto.InquiryRequest;
import com.webapp.websiteportal.entity.Inquiry;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.repository.InquiryRepository;
import com.webapp.websiteportal.service.IInquiryService;

@Service
public class InquiryServiceImpl implements IInquiryService{

	@Autowired
	private InquiryRepository inquiryRepository;

	@Override
	public void saveInquiryDetails(InquiryRequest inquiryRequest, Optional<WebSiteDetails> websiteDetails) {
	
		Inquiry inquiry = new Inquiry();
		inquiry.setFirstName(inquiryRequest.getFirstName());
		inquiry.setLastName(inquiryRequest.getLastName());
		inquiry.setEmail(inquiryRequest.getEmail());
		inquiry.setPhone(inquiryRequest.getPhone());
		inquiry.setTitle(inquiryRequest.getTitle());
		inquiry.setMessage(inquiryRequest.getMessage());
		inquiry.setWebSiteDetails(websiteDetails.get());
		
		inquiryRepository.save(inquiry);
	}
	
	

}
