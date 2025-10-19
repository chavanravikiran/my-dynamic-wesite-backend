package com.webapp.websiteportal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import com.webapp.websiteportal.dto.AvailableIntervalDTO;
import com.webapp.websiteportal.dto.BookSlotRequest;
import com.webapp.websiteportal.dto.CreateSlotRequest;
import com.webapp.websiteportal.entity.AppointmentSlot;
import com.webapp.websiteportal.entity.BookAppointment;
import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.entity.WebSiteDetails;
import jakarta.transaction.Transactional;

public interface IAppointmentService {

	public AppointmentSlot createSlot(CreateSlotRequest req, Users creator,WebSiteDetails webSiteDetail);

    @Transactional
    public BookAppointment bookSlot(BookSlotRequest req, Users user);

    @Transactional
    public BookAppointment cancelBooking(Long bookingId, Users user);

    @Transactional
    public void deactivateSlot(Long slotId, Users admin);
    
    public List<AppointmentSlot> createSlotNew(CreateSlotRequest req, Users creator, WebSiteDetails webSiteDetail);
    
    public Map<LocalDate, List<AvailableIntervalDTO>> getAvailableFromTodayV3(WebSiteDetails website);
    

}
