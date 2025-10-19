package com.webapp.websiteportal.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.webapp.websiteportal.entity.WebSiteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSlotRequest {

	private String serviceName;
	private WebSiteType websiteType;
//    private Long websiteKey; // WebSiteDetails.key
    private LocalDate date;
	private List<LocalDate> dates; // multiple date support
    private LocalTime fromTime;
    private LocalTime toTime;
    private Integer intervalMinutes;
    private Integer slotsPerInterval;
    private String notes;
    
    private List<SlotIntervalRequest> slots;  // multiple intervals

}
