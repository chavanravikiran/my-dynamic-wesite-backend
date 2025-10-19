package com.webapp.websiteportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.AppointmentDashboardView;
import com.webapp.websiteportal.entity.AppointmentDashboardViewId;

@Repository
//public interface AppointmentDashboardViewRepository extends JpaRepository<AppointmentDashboardView, LocalDate> {
public interface AppointmentDashboardViewRepository extends JpaRepository<AppointmentDashboardView, AppointmentDashboardViewId>{

}
