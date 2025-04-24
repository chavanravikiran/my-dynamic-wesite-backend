package com.webapp.websiteportal.service;

import java.util.Optional;

import com.webapp.websiteportal.dto.InquiryRequest;
import com.webapp.websiteportal.entity.WebSiteDetails;

public interface IInquiryService {

	void saveInquiryDetails(InquiryRequest inquiryRequest, Optional<WebSiteDetails> websiteDetails);
    
}