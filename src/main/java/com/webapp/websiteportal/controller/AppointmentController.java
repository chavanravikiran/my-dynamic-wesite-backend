package com.webapp.websiteportal.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.webapp.websiteportal.entity.AppointmentSlot;
import com.webapp.websiteportal.entity.BookAppointment;
import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.repository.AppointmentSlotRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.impl.AppointmentServiceImpl;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;

	@Autowired
	private AppointmentServiceImpl appointmentService;
	
	@Autowired
	private AppointmentSlotRepository slotRepository;

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
	}
	
	@PostMapping("/slotsNew")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createSlotV1(@RequestBody CreateSlotRequest req, @AuthenticationPrincipal Users currentUser) {
	    WebSiteDetails webSiteDetail = websiteDetailsRepository.findByWebSiteTypeAndIsActive(req.getWebsiteType(), 'Y');
	    if (webSiteDetail == null) {
	        return ResponseEntity.status(404).body("Website not found");
	    }

	    if (!currentUser.getRole().name().equals("ROLE_ADMIN") || !currentUser.getWebSiteDetails().equals(webSiteDetail)) {
	        return ResponseEntity.status(403).body("Not authorized to create slot for this website");
	    }

	    List<AppointmentSlot> created = appointmentService.createSlotNew(req, currentUser, webSiteDetail);
	    return ResponseEntity.ok(Map.of(
	            "message", created.size() + " slots created successfully",
	            "slots", created
	    ));
	}

	//Working 
	@PostMapping("/create-slot-v3")
	public ResponseEntity<?> createSlotV3(@RequestBody CreateSlotRequest request) {

	    List<AppointmentSlot> createdSlots = new ArrayList<>();

	    for (LocalDate date : request.getDates()) {

	        WebSiteDetails webSiteDetail =
	                websiteDetailsRepository.findByWebSiteTypeAndIsActive(request.getWebsiteType(), 'Y');

	        if (webSiteDetail == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Website not found");
	        }

	        // Generate interval slots between fromTime → toTime
	        LocalTime current = request.getFromTime();
	        LocalTime end = request.getToTime();

	        while (current.isBefore(end)) {
	            LocalTime next = current.plusMinutes(request.getIntervalMinutes());
	            if (next.isAfter(end)) {
	                break;
	            }

	            // Check for duplicate
	            boolean exists = slotRepository.existsByServiceNameAndDateAndFromTimeAndToTime(
	                    request.getWebsiteType().toString(), date, current, next);

	            if (exists) {
	                // skip duplicates but continue others
	                current = next;
	                continue;
	            }

	            AppointmentSlot slot = new AppointmentSlot();
	            slot.setServiceName(request.getWebsiteType().toString());
	            slot.setWebSiteDetails(webSiteDetail);
	            slot.setDate(date);
	            slot.setFromTime(current);
	            slot.setToTime(next);
	            slot.setIntervalMinutes(0); // because now this is a concrete interval
	            slot.setSlotsPerInterval(request.getSlotsPerInterval());
	            slot.setNotes(request.getNotes());

	            createdSlots.add(slotRepository.save(slot));

	            current = next;
	        }
	    }

	    return ResponseEntity.ok(createdSlots);
	}

	// Admin: deactivate slot
	@PutMapping("/slots/{slotId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deactivateSlot(@PathVariable Long slotId, @AuthenticationPrincipal Users currentUser) {
		appointmentService.deactivateSlot(slotId, currentUser);
		return ResponseEntity.ok("Slot deactivated");
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

	//new
	@GetMapping("/available-from-today-v3/{websiteKey}")
	public ResponseEntity<Map<LocalDate, List<AvailableIntervalDTO>>> getAvailableFromTodayV3(
	        @PathVariable Long websiteKey) {

	    WebSiteDetails website = websiteDetailsRepository.findById(websiteKey)
	            .orElseThrow(() -> new IllegalArgumentException("Website not found"));

	    Map<LocalDate, List<AvailableIntervalDTO>> result = appointmentService.getAvailableFromTodayV3(website);
	    return ResponseEntity.ok(result);
	}

	
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
