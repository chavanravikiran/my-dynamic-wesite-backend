package com.webapp.websiteportal.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.websiteportal.dto.FileModelResponse;
import com.webapp.websiteportal.dto.WebSiteDetailsResponse;
import com.webapp.websiteportal.dto.WebsiteResponse;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.entity.WebSiteType;
import com.webapp.websiteportal.repository.FeatureMenuRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.repository.WebsiteFeatureMenuRepository;
import com.webapp.websiteportal.service.IFeatureMenuService;
import com.webapp.websiteportal.service.IS3Service;
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
	 
	@Autowired
	private IS3Service s3Service;
	
	@Value("${application.rootpath.logoImage}")
    private String logoImage;
	
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
			
			FileModelResponse response1 = s3Service.downloadFileWithPath(webSiteDetails.get().getWebsiteLogo(),logoImage);
			
			response = websiteDetailsService.getWebsiteDetailsById(webSiteDetails.get().getKey(),response1);
		}else {
			System.out.println("Some thing went wrong !!!");
		}
	    return ResponseEntity.ok(response);
	}
}
