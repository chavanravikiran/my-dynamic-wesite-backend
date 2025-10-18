package com.webapp.websiteportal.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.service.IReportGeneration;

@RestController
@RequestMapping("/api/reports")
public class ReportGenerationController {
	
	@Autowired
	private IReportGeneration ReportGenerationService;

	@GetMapping("/generateAppointmentReceipt/{bookingId}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> generateAppointmentReceipt(@PathVariable Long bookingId, @AuthenticationPrincipal Users currentUser) {
	    try {
	        String base64Pdf = ReportGenerationService.generateBookingReceipt(bookingId, currentUser);
	        return ResponseEntity.ok(Map.of("receiptBase64", base64Pdf));
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body(e.getMessage());
	    }
	}
}
