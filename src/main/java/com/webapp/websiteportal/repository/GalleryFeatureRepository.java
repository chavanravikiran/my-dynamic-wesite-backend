package com.webapp.websiteportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.GalleryFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;

@Repository
public interface GalleryFeatureRepository extends JpaRepository<GalleryFeature, Long> {

	List<GalleryFeature> findByWebSiteDetailsAndIsActive(WebSiteDetails websiteDetails, Character isActive);

	

}
