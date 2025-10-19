package com.webapp.websiteportal.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.webapp.websiteportal.dto.AvailableIntervalDTO;
import com.webapp.websiteportal.dto.BookSlotRequest;
import com.webapp.websiteportal.dto.CreateSlotRequest;
import com.webapp.websiteportal.dto.MyAppointmentSummaryResponse;
import com.webapp.websiteportal.dto.PagedResponse;
import com.webapp.websiteportal.dto.SlotIntervalRequest;
import com.webapp.websiteportal.entity.AppointmentSlot;
import com.webapp.websiteportal.entity.BookAppointment;
import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.repository.AppointmentSlotRepository;
import com.webapp.websiteportal.repository.BookAppointmentRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IAppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements IAppointmentService{
	
	@Autowired
	private AppointmentSlotRepository slotRepo;
	
	@Autowired
    private BookAppointmentRepository bookRepo;
    
	@Autowired
	private WebsiteDetailsRepository webSiteDetailsRepository;

    @Transactional
    public AppointmentSlot createSlot(CreateSlotRequest req, Users creator,WebSiteDetails webSiteDetail) {
        WebSiteDetails website = webSiteDetailsRepository.findById(webSiteDetail.getKey())
                .orElseThrow(() -> new IllegalArgumentException("Website not found"));

        AppointmentSlot slot = AppointmentSlot.builder()
                .serviceName(req.getWebsiteType().toString())
                .webSiteDetails(website)
                .date(req.getDate())
                .fromTime(req.getFromTime())
                .toTime(req.getToTime())
                .intervalMinutes(req.getIntervalMinutes())
                .slotsPerInterval(req.getSlotsPerInterval())
                .notes(req.getNotes())
                .build();

        return slotRepo.save(slot);
    }

	@Transactional
	public List<AppointmentSlot> createSlotNew(CreateSlotRequest req, Users creator, WebSiteDetails webSiteDetail) {
	    WebSiteDetails website = webSiteDetailsRepository.findById(webSiteDetail.getKey())
	            .orElseThrow(() -> new IllegalArgumentException("Website not found"));

	    if (req.getSlots() == null || req.getSlots().isEmpty()) {
	        throw new IllegalArgumentException("At least one slot interval is required");
	    }

	    List<AppointmentSlot> savedSlots = new ArrayList<>();

	    for (SlotIntervalRequest s : req.getSlots()) {
	        AppointmentSlot slot = AppointmentSlot.builder()
	                .serviceName(req.getWebsiteType().toString())
	                .webSiteDetails(website)
	                .date(req.getDate())
	                .fromTime(s.getFromTime())
	                .toTime(s.getToTime())
	                .intervalMinutes((int) java.time.Duration.between(s.getFromTime(), s.getToTime()).toMinutes())
	                .slotsPerInterval(s.getSlotsPerInterval())
	                .notes(req.getNotes())
	                .build();

	        savedSlots.add(slot);
	    }

	    return slotRepo.saveAll(savedSlots);
	}

	
    @Transactional
    public BookAppointment bookSlot(BookSlotRequest req, Users user) {
        AppointmentSlot slot = slotRepo.findById(req.getSlotId())
                .orElseThrow(() -> new IllegalArgumentException("Slot not found"));

        // check same website
        if (!slot.getWebSiteDetails().equals(user.getWebSiteDetails())) {
            throw new SecurityException("User cannot book slot for a different website.");
        }

        // Only allow booking for future times
        if (slot.getDate().isBefore(req.getDate())) {
            throw new IllegalArgumentException("Booking date mismatch with slot date");
        }
        if (slot.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot book past dates");
        }

        // check interval alignment: startTime should be one of generated intervals
        boolean validInterval = false;
        LocalTime t = slot.getFromTime();
        while (!t.isAfter(slot.getToTime().minusMinutes(slot.getIntervalMinutes()))) {
            if (t.equals(req.getStartTime())) {
                validInterval = true;
                break;
            }
            t = t.plusMinutes(slot.getIntervalMinutes());
        }
        if (!validInterval) throw new IllegalArgumentException("Invalid interval start time");

        long booked = bookRepo.countByAppointmentSlotAndDateAndStartTimeAndStatus(slot, req.getDate(), req.getStartTime(), BookAppointment.Status.BOOKED);
        if (booked >= slot.getSlotsPerInterval()) {
            throw new IllegalStateException("No slots available for this interval");
        }

        BookAppointment booking = BookAppointment.builder()
                .appointmentSlot(slot)
                .user(user)
                .date(req.getDate())
                .startTime(req.getStartTime())
                .endTime(req.getStartTime().plusMinutes(slot.getIntervalMinutes()))
                .status(BookAppointment.Status.BOOKED)
                .build();

        return bookRepo.save(booking);
    }

    @Transactional
    public BookAppointment cancelBooking(Long bookingId, Users user) {
        BookAppointment booking = bookRepo.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        // only user who booked or admin of that website may cancel
        boolean isOwner = booking.getUser().getId().equals(user.getId());
        boolean isAdminOfSite = user.getRole().name().equals("ROLE_ADMIN") &&
                user.getWebSiteDetails().equals(booking.getAppointmentSlot().getWebSiteDetails());

        if (!isOwner && !isAdminOfSite) {
            throw new SecurityException("Not authorized to cancel this booking");
        }

        // only allow cancelling future bookings
        LocalDateTime intervalStart = LocalDateTime.of(booking.getDate(), booking.getStartTime());
        if (intervalStart.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot cancel past booking");
        }

        booking.setStatus(BookAppointment.Status.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());
        return bookRepo.save(booking);
    }

    @Transactional
    public void deactivateSlot(Long slotId, Users admin) {
        AppointmentSlot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new IllegalArgumentException("Slot not found"));
        if (!admin.getRole().name().equals("ROLE_ADMIN") ||
                !admin.getWebSiteDetails().equals(slot.getWebSiteDetails())) {
            throw new SecurityException("Not authorized to deactivate this slot");
        }
//        slot.setActive(false);
        slot.setIsActive('N');
        slotRepo.save(slot);
    }

 // Fetch paged & filtered appointments for the current user
    public PagedResponse<MyAppointmentSummaryResponse> getMyAppointments(
            Users user,
            Optional<String> statusOpt,
            Optional<LocalDate> dateFromOpt,
            Optional<LocalDate> dateToOpt,
            int page,
            int size) {

        LocalDate from = dateFromOpt.orElse(LocalDate.of(1900, 1, 1));
        LocalDate to = dateToOpt.orElse(LocalDate.of(2999, 12, 31));

        Pageable pageable = PageRequest.of(page, size,
                Sort.by("date").descending().and(Sort.by("startTime")));

        Page<BookAppointment> pageResult;

        if (statusOpt.isPresent()) {
            BookAppointment.Status status = BookAppointment.Status.valueOf(statusOpt.get().toUpperCase());
            pageResult = bookRepo.findByUserAndStatusAndDateBetween(user, status, from, to, pageable);
        } else {
            pageResult = bookRepo.findByUserAndDateBetween(user, from, to, pageable);
        }

        List<MyAppointmentSummaryResponse> content = pageResult.stream()
                .map(b -> new MyAppointmentSummaryResponse(
                        b.getKey(),
                        b.getAppointmentSlot().getServiceName(),
                        b.getDate(),
                        b.getStartTime(),
                        b.getEndTime(),
                        b.getStatus().name()
                ))
                .collect(Collectors.toList());

        return new PagedResponse<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isLast()
        );
    }


    // Confirm appointment (mark as COMPLETED)
    @Transactional
    public MyAppointmentSummaryResponse confirmAppointment(Long bookingId, Users user) {
        BookAppointment booking = bookRepo.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Not authorized to confirm this booking");
        }

        if (booking.getStatus() != BookAppointment.Status.BOOKED) {
            throw new IllegalStateException("Only BOOKED appointments can be confirmed");
        }

        booking.setStatus(BookAppointment.Status.COMPLETED);
        BookAppointment saved = bookRepo.save(booking);

        return new MyAppointmentSummaryResponse(
                saved.getKey(),
                saved.getAppointmentSlot().getServiceName(),
                saved.getDate(),
                saved.getStartTime(),
                saved.getEndTime(),
                saved.getStatus().name()
        );
    }


	@Override
	public Map<LocalDate, List<AvailableIntervalDTO>> getAvailableFromTodayV3(WebSiteDetails website) {
	    LocalDate today = LocalDate.now();
	    LocalTime now = LocalTime.now();
	
	    // Fetch all active slots for this website
	    List<AppointmentSlot> slots = slotRepo.findByWebSiteDetailsAndIsActive(website, 'Y')
	            .stream()
	            .sorted(Comparator.comparing(AppointmentSlot::getDate)
	                    .thenComparing(AppointmentSlot::getFromTime))
	            .toList();
	
	    List<AvailableIntervalDTO> allIntervals = new ArrayList<>();
	
	    for (AppointmentSlot slot : slots) {
	        // Skip slots before today
	        if (slot.getDate().isBefore(today)) continue;
	
	        // Skip intervals that already passed today
	        if (slot.getDate().isEqual(today) && slot.getToTime().isBefore(now)) continue;
	
	        long bookedCount = bookRepo.countByAppointmentSlotAndDateAndStartTimeAndStatus(
	                slot, slot.getDate(), slot.getFromTime(), BookAppointment.Status.BOOKED);
	
	        int available = Math.max(0, slot.getSlotsPerInterval() - (int) bookedCount);
	
	        allIntervals.add(new AvailableIntervalDTO(
	                slot.getServiceName(),
	                slot.getKey(),
	                slot.getFromTime().toString(),
	                slot.getToTime().toString(),
	                available,
	                slot.getDate()
	        ));
	    }
	
	    // Group by date for frontend
	    return allIntervals.stream()
	            .collect(Collectors.groupingBy(
	                    AvailableIntervalDTO::getDate,
	                    LinkedHashMap::new,
	                    Collectors.toList()
	            ));
		}
}
