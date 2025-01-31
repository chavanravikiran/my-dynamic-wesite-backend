package com.webapp.websiteportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.dto.ContactUsResponse;
import com.webapp.websiteportal.entity.ContactUsFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.repository.ContactUsRepository;
import com.webapp.websiteportal.service.IContactUsService;

@Service
public class ContactUsServiceImpl implements IContactUsService{

	@Autowired
	private ContactUsRepository contactUsRepository;
	
	@Override
	public ContactUsResponse getOfficesByWebsite(WebSiteDetails webSiteDetails) {
		List<ContactUsFeature> offices = contactUsRepository.findByIsActiveAndWebSiteDetailsOrderByDisplaySeqAsc('Y',webSiteDetails);
		if(offices != null) {
			return ContactUsResponse.fromEntity(offices);
		}else {
			return null;
		}
	}


}
