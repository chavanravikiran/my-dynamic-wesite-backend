package com.webapp.websiteportal.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.websiteportal.entity.AppointmentSlot;
import com.webapp.websiteportal.entity.BookAppointment;
import com.webapp.websiteportal.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BookAppointmentRepository extends JpaRepository<BookAppointment, Long> {

    long countByAppointmentSlotAndDateAndStartTimeAndStatus(AppointmentSlot slot, LocalDate date, LocalTime startTime, BookAppointment.Status status);

    List<BookAppointment> findByUserAndStatus(Users user, BookAppointment.Status status);

    List<BookAppointment> findByUserAndDateAfter(Users user, LocalDate date); // future bookings

    List<BookAppointment> findByAppointmentSlot(AppointmentSlot slot);
    
 // Simple pageable query for user (status optional)
    Page<BookAppointment> findByUserAndDateBetween(Users user, LocalDate start, LocalDate end, Pageable pageable);

    Page<BookAppointment> findByUserAndStatusAndDateBetween(Users user, BookAppointment.Status status, LocalDate start, LocalDate end, Pageable pageable);

}