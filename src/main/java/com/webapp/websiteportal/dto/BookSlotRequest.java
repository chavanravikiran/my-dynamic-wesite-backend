package com.webapp.websiteportal.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSlotRequest {

	private Long slotId;
    private LocalDate date;
    private LocalTime startTime; // the interval start time user wants
}
