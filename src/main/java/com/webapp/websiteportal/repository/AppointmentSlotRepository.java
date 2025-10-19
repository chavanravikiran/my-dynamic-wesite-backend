package com.webapp.websiteportal.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.AppointmentSlot;
import com.webapp.websiteportal.entity.WebSiteDetails;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {
    
	List<AppointmentSlot> findByWebSiteDetailsAndDateAndIsActive(WebSiteDetails webSiteDetails, LocalDate date,Character flag);
    
	List<AppointmentSlot> findByWebSiteDetailsAndIsActive(WebSiteDetails webSiteDetails,Character flag);
	
	boolean existsByServiceNameAndDateAndFromTimeAndToTime(
	        String serviceName, LocalDate date, LocalTime fromTime, LocalTime toTime);
	
}