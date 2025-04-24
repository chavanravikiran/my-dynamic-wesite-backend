package com.webapp.websiteportal.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.websiteportal.dto.InquiryRequest;
import com.webapp.websiteportal.dto.MessageResponse;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.repository.InquiryRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IInquiryService;

@RestController
@RequestMapping("/api/users")
public class InquiryController {

	@Autowired
	private InquiryRepository inquiryRepository;

	@Autowired
	private IInquiryService inquiryService;

	@Autowired
	private WebsiteDetailsRepository webSiteDetailsRepository;

	@PostMapping("/submitinquiry")
	public MessageResponse submitInquiry(@RequestBody InquiryRequest inquiryRequest) {
		try {
			Optional<WebSiteDetails> websiteDetails = webSiteDetailsRepository
					.findByWebSiteType(inquiryRequest.getWebsiteType());
			if (websiteDetails.isEmpty()) {
				return MessageResponse.error("Website is not present for type: " + inquiryRequest.getWebsiteType());
			}
			inquiryService.saveInquiryDetails(inquiryRequest, websiteDetails);
			return MessageResponse.successWithMsg("Inquiry submitted successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageResponse.error("An error occurred while submitting the inquiry: " + e.getMessage());
		}
	}
}
