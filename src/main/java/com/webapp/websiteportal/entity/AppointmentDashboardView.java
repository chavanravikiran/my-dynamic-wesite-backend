package com.webapp.websiteportal.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "appointment_dashboard_view")
public class AppointmentDashboardView {

    @EmbeddedId
    private AppointmentDashboardViewId id;

    private Integer slotsPerInterval;
    private Long bookedCount;
    private Long remainingCount;

}

