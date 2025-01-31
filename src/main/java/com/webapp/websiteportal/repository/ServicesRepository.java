package com.webapp.websiteportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.ServicesFeature;

@Repository
public interface ServicesRepository extends JpaRepository<ServicesFeature, Long> {

	@Query("SELECT s FROM ServicesFeature s WHERE s.webSiteDetails.key = ?1 and s.isActive =?2 order by displaySeq asc")
	List<ServicesFeature> findByWebSiteDetailsAndIsActive(Long webSiteDetailId, Character isActive);

}
