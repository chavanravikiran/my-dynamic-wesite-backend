package com.webapp.websiteportal.service;

import com.webapp.websiteportal.dto.PortfolioModel;
import com.webapp.websiteportal.entity.StudentPortfolio;
import com.webapp.websiteportal.entity.WebSiteDetails;

public interface IStudentPortfolioService {

	PortfolioModel getPortfolioById(Long key);

	StudentPortfolio findByWebSiteDetails(WebSiteDetails webSiteDetails);

}
