package com.webapp.websiteportal.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment_slots")
public class AppointmentSlot extends AbstractEntity{
	
	private static final long serialVersionUID = 4267215461109423560L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "appointment_slots_seq", sequenceName = "appointment_slots_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "appointment_slots_seq")
	private Long key;
	
	// The service the slot belongs to (e.g., "PAN_CARD", "REGISTRATION")
    @Column(nullable = false, length = 200)
    private String serviceName;

    // Which website this slot belongs to
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "website_key", referencedColumnName = "key", nullable = false)
    private WebSiteDetails webSiteDetails;

    // Specific date for this slot (for one-day schedule).
    // For a more advanced recurring schedule you would add recurrence rules.
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime fromTime;

    @Column(nullable = false)
    private LocalTime toTime;

    // Interval in minutes for separate appointment windows e.g., 15 means windows: 10:00-10:15, 10:15-10:30...
    @Column(nullable = false)
    private Integer intervalMinutes;

    // Number of allowed bookings per interval
    @Column(nullable = false)
    private Integer slotsPerInterval;

    // Optional: admin-friendly description
    private String notes;

//    // Soft delete / disabled (admin can deactivate rather than delete)
//    private boolean active = true;
//    
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    @PrePersist
//    public void prePersist() {
//        createdAt = LocalDateTime.now();  // automatically set createdAt before insert
//    }
    
    
}
