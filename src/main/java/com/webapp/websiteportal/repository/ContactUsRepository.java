package com.webapp.websiteportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.ContactUsFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUsFeature, Long> {

	List<ContactUsFeature> findByIsActiveAndWebSiteDetailsOrderByDisplaySeqAsc(Character isActive, WebSiteDetails webSiteDetails);

}
