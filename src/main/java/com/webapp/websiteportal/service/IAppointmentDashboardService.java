package com.webapp.websiteportal.service;

import java.util.List;

import com.webapp.websiteportal.entity.AppointmentDashboardView;

public interface IAppointmentDashboardService {
    
	public List<AppointmentDashboardView> getDashboard();
}