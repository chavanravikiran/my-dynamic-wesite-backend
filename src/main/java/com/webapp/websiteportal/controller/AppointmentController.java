package com.webapp.websiteportal.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.websiteportal.dto.AppointmentResponse;
import com.webapp.websiteportal.dto.AvailableIntervalDTO;
import com.webapp.websiteportal.dto.BookSlotRequest;
import com.webapp.websiteportal.dto.CreateSlotRequest;
import com.webapp.websiteportal.dto.MyAppointmentSummaryResponse;
import com.webapp.websiteportal.dto.PagedResponse;
import com.webapp.websiteportal.entity.BookAppointment;
import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.impl.AppointmentServiceImpl;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;

	@Autowired
	private AppointmentServiceImpl appointmentService;

	// Admin: create slot (only ROLE_ADMIN and must belong to same website)
	@PostMapping("/slots")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createSlot(@RequestBody CreateSlotRequest req,@AuthenticationPrincipal Users currentUser) {
		// ensure admin belongs to same website
		WebSiteDetails webSiteDetail=websiteDetailsRepository.findByWebSiteTypeAndIsActive(req.getWebsiteType(),'Y');
		if(webSiteDetail !=null) {
			if (!currentUser.getRole().name().equals("ROLE_ADMIN") || !currentUser.getWebSiteDetails().equals(webSiteDetail)) {
				return ResponseEntity.status(403).body("Not authorized to create slot for this website");
			}
			return ResponseEntity.ok(appointmentService.createSlot(req, currentUser,webSiteDetail));
		}else {
			return ResponseEntity.status(404).body("Website not found");
		}
//		WebSiteDetails web = websiteDetailsRepository.findById(webSiteDetail.getKey())
//				.orElseThrow(() -> new IllegalArgumentException("Website not found"));

	}

	// Admin: deactivate slot
	@PutMapping("/slots/{slotId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deactivateSlot(@PathVariable Long slotId, @AuthenticationPrincipal Users currentUser) {
		appointmentService.deactivateSlot(slotId, currentUser);
		return ResponseEntity.ok("Slot deactivated");
	}

	// Get intervals for a slot
	@GetMapping("/slots/{slotId}/intervals")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<AvailableIntervalDTO>> getIntervals(@PathVariable Long slotId,
			@AuthenticationPrincipal Users currentUser) {
		// security: only allow if user's website equals slot's website
		return ResponseEntity.ok(appointmentService.getAvailableIntervals(slotId));
	}

	// Get available intervals by websiteKey and date (user will call with their
	// website)
	@GetMapping("/website/{websiteKey}/date/{date}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> getByWebsiteAndDate(@PathVariable Long websiteKey, @PathVariable String date,
			@AuthenticationPrincipal Users currentUser) {
		WebSiteDetails web = websiteDetailsRepository.findById(websiteKey)
				.orElseThrow(() -> new IllegalArgumentException("Website not found"));

		if (!currentUser.getWebSiteDetails().equals(web)) {
			return ResponseEntity.status(403).body("Not authorized for this website");
		}

		LocalDate localDate = LocalDate.parse(date); // expect yyyy-MM-dd
		return ResponseEntity.ok(appointmentService.getAvailableByWebsiteAndDate(web, localDate));
	}

	// Book (ROLE_USER)
	@PostMapping("/book")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> book(@RequestBody BookSlotRequest req, @AuthenticationPrincipal Users currentUser) {
		try {
			BookAppointment booking = appointmentService.bookSlot(req, currentUser);
			AppointmentResponse response = new AppointmentResponse(
		            booking.getKey(),
		            booking.getAppointmentSlot().getServiceName(),
		            booking.getDate(),
		            booking.getStartTime(),
		            booking.getEndTime(),
		            booking.getStatus().name()
		        );
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException | IllegalStateException | SecurityException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	// Cancel booking (user or admin of site)
	@PostMapping("/cancel/{bookingId}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId, @AuthenticationPrincipal Users currentUser) {
		try {
			BookAppointment b = appointmentService.cancelBooking(bookingId, currentUser);
			return ResponseEntity.ok(b);
		} catch (IllegalArgumentException | IllegalStateException | SecurityException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@GetMapping("/website/{websiteKey}/from-today")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> getSlotsFromToday(@PathVariable Long websiteKey, @AuthenticationPrincipal Users currentUser) {
	    WebSiteDetails web = websiteDetailsRepository.findById(websiteKey)
	            .orElseThrow(() -> new IllegalArgumentException("Website not found"));

	    if (!currentUser.getWebSiteDetails().equals(web)) {
	        return ResponseEntity.status(403).body("Not authorized for this website");
	    }

	    return ResponseEntity.ok(appointmentService.getAvailableFromToday(web));
	}

	// GET /api/appointments/my?page=0&size=10&status=BOOKED&dateFrom=2025-10-01&dateTo=2025-10-31
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> myAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam Optional<String> status,
            @RequestParam Optional<String> dateFrom,
            @RequestParam Optional<String> dateTo,
            @AuthenticationPrincipal Users currentUser) {

        Optional<LocalDate> from = dateFrom.map(LocalDate::parse);
        Optional<LocalDate> to = dateTo.map(LocalDate::parse);

        PagedResponse<MyAppointmentSummaryResponse> resp = appointmentService.getMyAppointments(currentUser, status, from, to, page, size);
        return ResponseEntity.ok(resp);
    }

    // POST confirm -Once Appointment Done
    @PostMapping("/confirm/{bookingId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> confirm(@PathVariable Long bookingId, @AuthenticationPrincipal Users currentUser) {
        try {
        	MyAppointmentSummaryResponse dto = appointmentService.confirmAppointment(bookingId, currentUser);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException | IllegalStateException | SecurityException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
