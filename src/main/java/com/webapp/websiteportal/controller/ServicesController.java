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

import com.webapp.websiteportal.dto.ServiceResponseDTO;
import com.webapp.websiteportal.entity.ServicesFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.entity.WebSiteType;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IServicesService;
import com.webapp.websiteportal.service.IWebsiteDetailsService;

@RestController
@RequestMapping("/openApi")
public class ServicesController {

	@Autowired
	private IWebsiteDetailsService websiteDetailsService;
	
	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;
	
	@Autowired
	private IServicesService servicesService;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/fetchService/{websiteType}")
	public List<ServiceResponseDTO> fetchWebsiteServicesNew(@PathVariable WebSiteType  websiteType) {
		WebSiteDetails webSiteDetail=websiteDetailsRepository.findByWebSiteTypeAndIsActive(websiteType,'Y');
		List<ServicesFeature> services = null;
		if(webSiteDetail != null) {
			return servicesService.fetchActiveServices(webSiteDetail);
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Website details not found for the provided website type.");
		}
	}
	
}
