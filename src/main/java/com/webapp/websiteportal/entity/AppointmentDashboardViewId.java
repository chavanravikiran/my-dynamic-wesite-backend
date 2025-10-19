package com.webapp.websiteportal.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;

//@Entity
//@Data
//@Table(name = "appointment_dashboard_view")
//public class AppointmentDashboardView {
//
////    @Id
////    private LocalDate date;
////
////    private Long totalSlots;
////    private Long bookedCount;
////    private Long remainingCount;
//
//	@Id
//    private LocalDate date;
//
//    private LocalTime fromTime;
//    private LocalTime toTime;
//    private Integer slotsPerInterval;
//    private Long bookedCount;
//    private Long remainingCount;
//}

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Embeddable
public class AppointmentDashboardViewId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LocalDate date;
    private LocalTime fromTime;
    private LocalTime toTime;
}
