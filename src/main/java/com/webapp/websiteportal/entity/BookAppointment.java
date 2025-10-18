package com.webapp.websiteportal.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "book_appointments")
public class BookAppointment extends AbstractEntity{
	
	private static final long serialVersionUID = 5824607278809437686L;

	public enum Status { BOOKED, CANCELLED ,COMPLETED}
	 
	@Id
    @SequenceGenerator(name = "book_appointment_seq", sequenceName = "book_appointment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_appointment_seq")
    private Long key;

    // Which slot definition it belongs to
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_slot_id", nullable = false)
    private AppointmentSlot appointmentSlot;

    // The user who booked
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // Planned date and time for the booked interval start
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    // optional endTime (can be derived from interval)
    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.BOOKED;

//    @Column(nullable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime cancelledAt;
    
}
