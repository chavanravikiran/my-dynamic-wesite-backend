package com.webapp.websiteportal.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.webapp.websiteportal.entity.BookAppointment;
import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.repository.BookAppointmentRepository;
import com.webapp.websiteportal.service.IReportGeneration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportGenerationServiceImpl implements IReportGeneration {
	
	@Autowired
	private BookAppointmentRepository bookAppointmentRepository; 
	
	@Override
	public String generateBookingReceipt(Long bookingId, Users currentUser) throws Exception {
		BookAppointment booking = bookAppointmentRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        // authorization check
        boolean isOwner = booking.getUser().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole().name().equals("ROLE_ADMIN");
        if (!isOwner && !isAdmin) throw new SecurityException("Unauthorized");

        // Prepare data for Jasper
        Map<String, Object> params = new HashMap<>();
        params.put("serviceName", booking.getAppointmentSlot().getServiceName());
        params.put("date", booking.getDate().toString());
        params.put("startTime", booking.getStartTime().toString());
        params.put("endTime", booking.getEndTime().toString());
        params.put("status", booking.getStatus().name());
        params.put("userName", booking.getUser().getName());

        // Your JRXML template path (e.g., src/main/resources/reports/appointment_receipt.jrxml)
        JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/appointment_receipt.jrxml"));

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(List.of(booking));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        // Export to PDF
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);

        // Convert PDF bytes to Base64 string
        return Base64.getEncoder().encodeToString(out.toByteArray());
	}


}