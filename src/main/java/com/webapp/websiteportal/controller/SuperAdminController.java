package com.webapp.websiteportal.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.webapp.websiteportal.dto.FeatureMenuRequest;
import com.webapp.websiteportal.dto.MessageResponse;
import com.webapp.websiteportal.dto.WebsiteResponse;
import com.webapp.websiteportal.entity.FeatureMenu;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IFeatureMenuService;
import com.webapp.websiteportal.service.IWebsiteDetailsService;
import com.webapp.websiteportal.service.impl.AppointmentServiceImpl;

@RestController
@RequestMapping("/api/superAdmin")
public class SuperAdminController {

	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;

	@Autowired
	private IWebsiteDetailsService WebsiteDetailsService;

	@Autowired
	private AppointmentServiceImpl appointmentService;
	
	@Autowired
	private IFeatureMenuService featureMenuService;
	
	@GetMapping("/websiteDetails")
	private List<WebsiteResponse> getAllWebsiteDetails() {
		return WebsiteDetailsService.findAll();
	}
	@GetMapping("/fetchAllFeature")
	private List<FeatureMenu> createNewFeature() {
		return featureMenuService.findAllByOrderByKeyAsc();
	}
	
	//Add feature
	@PostMapping("/addFeature")
	public MessageResponse addFeature(@RequestBody FeatureMenuRequest featureMenuRequest) {
		return featureMenuService.addNewFeature(featureMenuRequest);
	}
	
	@GetMapping("/test")
	public String test() {
	    return "SuperAdminController loaded";
	}
}
