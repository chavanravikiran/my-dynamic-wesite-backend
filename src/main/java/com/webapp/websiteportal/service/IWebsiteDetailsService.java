package com.webapp.websiteportal.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.webapp.websiteportal.dto.FileModelResponse;
import com.webapp.websiteportal.dto.WebSiteDetailsResponse;
import com.webapp.websiteportal.dto.WebsiteResponse;
import com.webapp.websiteportal.entity.WebSiteDetails;

public interface IWebsiteDetailsService {

	public WebsiteResponse fetchWebSiteDetails();

//	public Optional<WebSiteDetails> findByKeyAndIsActive(Long websiteId, Character isActive);

	public WebSiteDetailsResponse getWebsiteDetailsById(Long websiteId);

	public WebSiteDetailsResponse getWebsiteDetailsById(Long key, FileModelResponse response1);

}
