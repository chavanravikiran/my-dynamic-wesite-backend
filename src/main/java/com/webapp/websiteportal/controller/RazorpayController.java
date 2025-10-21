package com.webapp.websiteportal.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.repository.AppointmentSlotRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IRazorpayService;
import com.webapp.websiteportal.service.impl.AppointmentServiceImpl;

@RestController
@RequestMapping("/api/payments")
public class RazorpayController {

	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;

	@Autowired
	private AppointmentServiceImpl appointmentService;
	
	@Autowired
	private AppointmentSlotRepository slotRepository;

	@Autowired
	private IRazorpayService razorpayService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestParam Long applicationId,@RequestParam BigDecimal amount,@AuthenticationPrincipal Users currentUser) {
        try {
            Map<String, Object> data = razorpayService.createPayment(applicationId, amount,currentUser);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> payload) {
        try {
            boolean ok = razorpayService.verifyPayment(payload);
            return ok ? ResponseEntity.ok(Map.of("status", "success"))
                      : ResponseEntity.badRequest().body(Map.of("status", "failure"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
	
}
