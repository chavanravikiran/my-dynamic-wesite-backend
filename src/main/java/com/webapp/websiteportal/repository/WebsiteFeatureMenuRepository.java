package com.webapp.websiteportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.dto.FeatureListResponse;
import com.webapp.websiteportal.entity.FeatureMenu;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.entity.WebsiteFeatureMenu;

@Repository
public interface WebsiteFeatureMenuRepository extends JpaRepository<WebsiteFeatureMenu, Long> {

	List<WebsiteFeatureMenu> findByIsActiveAndWebsiteDetails(Character isActive,WebSiteDetails webSiteDetails);

//	List<WebsiteFeatureMenu> findByWebSiteDetails(WebSiteDetails websiteDetails);
//
//    List<WebsiteFeatureMenu> findByWebsiteDetailsKey(Long websiteId);
//	 
//	 @Query("SELECT wfm.featureMenu FROM WebsiteFeatureMenu wfm WHERE wfm.webSiteDetails.key = :websiteId")
//	 List<FeatureMenu> findFeaturesByWebsiteId(@Param("websiteId") Long websiteId);

}