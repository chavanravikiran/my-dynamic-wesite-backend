package com.webapp.websiteportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.entity.AppointmentDashboardView;
import com.webapp.websiteportal.repository.AppointmentDashboardViewRepository;
import com.webapp.websiteportal.service.IAppointmentDashboardService;

@Service
public class AppointmentDashboardServiceImpl implements IAppointmentDashboardService {

	@Autowired
	private AppointmentDashboardViewRepository dashboardRepo;

	@Override
	public List<AppointmentDashboardView> getDashboard() {
	    return dashboardRepo.findAll(Sort.by("id.date", "id.fromTime"));
	}
}
