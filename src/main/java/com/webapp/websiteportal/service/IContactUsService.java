package com.webapp.websiteportal.service;

import com.webapp.websiteportal.dto.ContactUsResponse;
import com.webapp.websiteportal.entity.WebSiteDetails;

public interface IContactUsService {

	ContactUsResponse getOfficesByWebsite(WebSiteDetails webSiteDetail);


}
