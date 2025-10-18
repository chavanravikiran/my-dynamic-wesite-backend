package com.webapp.websiteportal.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AvailableIntervalDTO {

	private String serviceName;
    private Long slotId;
    private String startTime;
    private String endTime;
    private int available; // how many spots left
    private LocalDate date;  // NEW
    
    public AvailableIntervalDTO(String serviceName, Long slotId, String startTime, String endTime, int available,LocalDate date) {
        this.serviceName = serviceName;
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
        this.date = date;
    }

    // Optional: default constructor
    public AvailableIntervalDTO() {}
}
