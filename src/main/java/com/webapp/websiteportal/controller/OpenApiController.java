package com.webapp.websiteportal.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.websiteportal.dto.WebSiteDetailsResponse;
import com.webapp.websiteportal.dto.WebsiteResponse;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.entity.WebSiteType;
import com.webapp.websiteportal.repository.FeatureMenuRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.repository.WebsiteFeatureMenuRepository;
import com.webapp.websiteportal.service.IFeatureMenuService;
import com.webapp.websiteportal.service.IWebsiteDetailsService;

//@CrossOrigin
@RestController
@RequestMapping("/openApi")
public class OpenApiController {

	@Autowired
	private IWebsiteDetailsService websiteDetailsService;
	
	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;
	
	@Autowired
	private WebsiteFeatureMenuRepository websiteFeatureMenuRepository;
	
	@Autowired
	private FeatureMenuRepository featureMenuRepository;
	
	@Autowired
	private IFeatureMenuService featureMenuService;
	 
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/websiteData")
	public WebsiteResponse getWebSiteDetails()  {
		return websiteDetailsService.fetchWebSiteDetails();
	}
	
	@GetMapping("/{websiteType}")
	public ResponseEntity<WebSiteDetailsResponse> getWebsiteDetails(@PathVariable WebSiteType  websiteType) {
		Optional<WebSiteDetails> webSiteDetails=websiteDetailsRepository.findByWebSiteType(websiteType);
		WebSiteDetailsResponse response=null; 
		if(webSiteDetails.isPresent()) {
			response = websiteDetailsService.getWebsiteDetailsById(webSiteDetails.get().getKey());
		}else {
			System.out.println("Some thing went wrong !!!");
		}
	    return ResponseEntity.ok(response);
	}
}
