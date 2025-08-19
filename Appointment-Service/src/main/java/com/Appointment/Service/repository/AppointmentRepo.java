package com.Appointment.Service.repository;

import com.Appointment.Service.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    List<Appointment> findByUsername (String username);
    List<Appointment> findByGarageId (Long garageId);
}
