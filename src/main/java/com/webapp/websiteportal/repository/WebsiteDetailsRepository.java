package com.webapp.websiteportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.entity.WebSiteType;

@Repository
public interface WebsiteDetailsRepository extends JpaRepository<WebSiteDetails, Long> {

	Optional<WebSiteDetails> findByKeyAndIsActive(Long key, Character isActive);

	Optional<WebSiteDetails> findByWebSiteType(WebSiteType websiteType);

	WebSiteDetails findByWebSiteTypeAndIsActive(WebSiteType websiteType, Character isActive);

	List<WebSiteDetails> findByIsActive(Character isActive);

	List<WebSiteDetails> findAllByOrderByKeyAsc();

}
