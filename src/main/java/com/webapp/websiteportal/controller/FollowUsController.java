package com.webapp.websiteportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.webapp.websiteportal.dto.FollowUsResponse;
import com.webapp.websiteportal.entity.FollowUsFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.entity.WebSiteType;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IFollowUsService;

@RestController
@RequestMapping("/openApiForFollowUs")
public class FollowUsController {
	
	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;
	
	@Autowired
	private IFollowUsService followUsService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/fetchFollowUs/{websiteType}")
	public List<FollowUsResponse> fetchFollowUs(@PathVariable WebSiteType websiteType){
		WebSiteDetails webSiteDetail = websiteDetailsRepository.findByWebSiteTypeAndIsActive(websiteType,'Y');
		if(webSiteDetail != null) {
			return FollowUsResponse.init(followUsService.fetchActiveFollowUsData(webSiteDetail));
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Website details not found for the provided website type.");
		}
	}
	
	//get latitude and longitude
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/fetchLocation/{websiteType}")
	public List<FollowUsResponse> fetchfetchLocation(@PathVariable WebSiteType websiteType){
		WebSiteDetails webSiteDetail = websiteDetailsRepository.findByWebSiteTypeAndIsActive(websiteType,'Y');
		if(webSiteDetail != null) {
			return FollowUsResponse.init(followUsService.fetchActiveFollowUsData(webSiteDetail));
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Website details not found for the provided website type.");
		}
	}
	
}
