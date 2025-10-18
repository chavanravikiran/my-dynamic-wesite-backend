package com.webapp.websiteportal.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

	 private Long bookingId;
	    private String serviceName;
	    private LocalDate date;
	    private LocalTime startTime;
	    private LocalTime endTime;
	    private String status;
}
