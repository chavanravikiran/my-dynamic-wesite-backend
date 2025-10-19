package com.webapp.websiteportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.websiteportal.entity.AppointmentDashboardView;
import com.webapp.websiteportal.service.IAppointmentDashboardService;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentDashboardController {

	@Autowired
	private IAppointmentDashboardService dashboardService;
	
	@GetMapping("/dashboard")
    public List<AppointmentDashboardView> getDashboard() {
        return dashboardService.getDashboard();
    }

}
