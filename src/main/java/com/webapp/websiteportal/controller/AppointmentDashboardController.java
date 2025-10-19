package com.webapp.websiteportal.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.websiteportal.entity.AppointmentDashboardView;
import com.webapp.websiteportal.repository.AppointmentSlotRepository;
import com.webapp.websiteportal.service.IAppointmentDashboardService;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentDashboardController {

	@Autowired
	private IAppointmentDashboardService dashboardService;
	
	@Autowired
	private AppointmentSlotRepository appointmentSlotRepository;
	
	@GetMapping("/dashboard")
    public List<AppointmentDashboardView> getDashboard() {
        return dashboardService.getDashboard();
    }
	
	@PutMapping("/slots/modify")
    @Transactional
    public ResponseEntity<String> modifySlot(
            @RequestParam String action,
            @RequestParam LocalDate date,
            @RequestParam LocalTime fromTime,
            @RequestParam LocalTime toTime,
            @RequestParam(required = false) Integer slotsPerInterval) {

        int updated = 0;

        if ("cancel".equalsIgnoreCase(action)) {
            updated = appointmentSlotRepository.cancelSlot(date, fromTime, toTime);
        } else if ("update".equalsIgnoreCase(action) && slotsPerInterval != null) {
            updated = appointmentSlotRepository.updateSlotCount(date, fromTime, toTime, slotsPerInterval);
        } else {
            return ResponseEntity.badRequest().body("Invalid action or missing slotsPerInterval");
        }

        if (updated > 0) {
            String message = "cancel".equalsIgnoreCase(action)
                    ? "Slot cancelled successfully"
                    : "Slot count updated successfully";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body("No matching slot found or no changes made");
        }
    }

}
