package com.luizhenriue.crudapi.repository;

import com.luizhenriue.crudapi.entity.Appointment;
import com.luizhenriue.crudapi.model.AppointmentStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStatusOrderByAppointmentDateTimeAsc(AppointmentStatus status);

    List<Appointment> findByAppointmentDateTimeBetweenOrderByAppointmentDateTimeAsc(LocalDateTime start, LocalDateTime end);

    List<Appointment> findByBarberIdAndStatusOrderByAppointmentDateTimeAsc(Long barberId, AppointmentStatus status);
}
