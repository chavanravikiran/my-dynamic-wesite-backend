package com.webapp.websiteportal.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.websiteportal.dto.GalleryResponse;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.entity.WebSiteType;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IGalleryService;
import com.webapp.websiteportal.service.IWebsiteDetailsService;

@RestController
@RequestMapping("/openApiGallery")
public class GalleryController {

	@Autowired
	private IWebsiteDetailsService websiteDetailsService;
	
	@Autowired
	private WebsiteDetailsRepository webSiteDetailsRepository;
	
	@Autowired
	private IGalleryService galleryService;
	
	@GetMapping("/fetchGallery/{websiteType}")
	public ResponseEntity<GalleryResponse> getGalleryByWebsiteType(@PathVariable WebSiteType websiteType) {
		 Optional<WebSiteDetails> websiteDetails = webSiteDetailsRepository.findByWebSiteType(websiteType);
		 GalleryResponse response = null;
		 if (websiteDetails.isEmpty()) {
		        return ResponseEntity.notFound().build();
		 }else {
			 response = galleryService.getGalleryByWebsiteType(websiteType);
		 }
		 if (response == null) {
	            return ResponseEntity.notFound().build();
	     }
		 return ResponseEntity.ok(response);
	}
}
