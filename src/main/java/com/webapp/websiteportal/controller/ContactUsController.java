package com.webapp.websiteportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.webapp.websiteportal.dto.ContactUsResponse;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.entity.WebSiteType;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IContactUsService;

@RestController
@RequestMapping("/api/contact-us")
public class ContactUsController {
	
	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;
	
	@Autowired
	private IContactUsService contactUsService;

	@GetMapping("/{websiteType}")
    public ResponseEntity<ContactUsResponse> getOfficesByWebsite(@PathVariable WebSiteType websiteType) {
		WebSiteDetails webSiteDetail=websiteDetailsRepository.findByWebSiteTypeAndIsActive(websiteType,'Y');
		if(webSiteDetail != null) {
			return ResponseEntity.ok(contactUsService.getOfficesByWebsite(webSiteDetail));
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Website details not found for the provided website type.");
		}
    }
	
	
}
