package com.webapp.websiteportal.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.AppointmentSlot;
import com.webapp.websiteportal.entity.WebSiteDetails;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {
    
	List<AppointmentSlot> findByWebSiteDetailsAndDateAndIsActive(WebSiteDetails webSiteDetails, LocalDate date,Character flag);
    
	List<AppointmentSlot> findByWebSiteDetailsAndIsActive(WebSiteDetails webSiteDetails,Character flag);
	
	boolean existsByServiceNameAndDateAndFromTimeAndToTime(
	        String serviceName, LocalDate date, LocalTime fromTime, LocalTime toTime);

	@Modifying
    @Query("UPDATE AppointmentSlot s SET s.isActive = 'N' " +
           "WHERE s.date = :date AND s.fromTime = :fromTime AND s.toTime = :toTime")
    int cancelSlot(@Param("date") LocalDate date,
                   @Param("fromTime") LocalTime fromTime,
                   @Param("toTime") LocalTime toTime);

    @Modifying
    @Query("UPDATE AppointmentSlot s SET s.slotsPerInterval = :slotsPerInterval " +
           "WHERE s.date = :date AND s.fromTime = :fromTime AND s.toTime = :toTime")
    int updateSlotCount(@Param("date") LocalDate date,
                        @Param("fromTime") LocalTime fromTime,
                        @Param("toTime") LocalTime toTime,
                        @Param("slotsPerInterval") Integer slotsPerInterval);
	
}