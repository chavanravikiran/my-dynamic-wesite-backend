package com.webapp.websiteportal.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.websiteportal.dto.PortfolioModel;
import com.webapp.websiteportal.entity.StudentPortfolio;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.entity.WebSiteType;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IStudentPortfolioService;

@RestController
@RequestMapping("/api/student")
public class PortfolioController {
	
	@Autowired
    private IStudentPortfolioService studentPortfolioService;

	@Autowired
	private WebsiteDetailsRepository webSiteDetailsRepository;
		 
    @GetMapping("/{websiteType}")
    public ResponseEntity<PortfolioModel> getStudentPortfolio(@PathVariable WebSiteType websiteType) {
    	Optional<WebSiteDetails> websiteDetails = webSiteDetailsRepository.findByWebSiteType(websiteType);
    	PortfolioModel portfolio=null;
		 if (websiteDetails.isEmpty()) {
		        return ResponseEntity.notFound().build();
		 }else {
			 StudentPortfolio studentPortfolio=studentPortfolioService.findByWebSiteDetails(websiteDetails.get());
			 portfolio = studentPortfolioService.getPortfolioById(studentPortfolio.getKey());
		 }
		 
        return ResponseEntity.ok(portfolio);
    }
}
