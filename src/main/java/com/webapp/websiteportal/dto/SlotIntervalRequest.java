package com.webapp.websiteportal.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotIntervalRequest {

	private LocalTime fromTime;
    private LocalTime toTime;
    private Integer slotsPerInterval;
}
