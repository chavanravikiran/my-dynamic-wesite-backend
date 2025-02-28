package com.webapp.websiteportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.StudentPortfolio;
import com.webapp.websiteportal.entity.WebSiteDetails;

@Repository
public interface StudentPortfolioRepository extends JpaRepository<StudentPortfolio, Long> {

	StudentPortfolio findByWebSiteDetailsAndIsActive(WebSiteDetails webSiteDetails, Character isActive);

}
