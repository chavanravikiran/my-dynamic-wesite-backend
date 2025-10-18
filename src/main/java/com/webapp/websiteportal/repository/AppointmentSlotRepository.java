package com.webapp.websiteportal.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.AppointmentSlot;
import com.webapp.websiteportal.entity.ContactUsFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {
    
	List<AppointmentSlot> findByWebSiteDetailsAndDateAndIsActive(WebSiteDetails webSiteDetails, LocalDate date,Character flag);
    
	List<AppointmentSlot> findByWebSiteDetailsAndIsActive(WebSiteDetails webSiteDetails,Character flag);
}