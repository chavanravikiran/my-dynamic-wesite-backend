package com.webapp.websiteportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.FollowUsFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;

@Repository
public interface FollowUsFeatureRepository extends JpaRepository<FollowUsFeature, Long> {

	List<FollowUsFeature> findByIsActiveAndWebSiteDetailsOrderByDisplaySeqAsc(Character isActive, WebSiteDetails webSiteDetail);

}
