package com.Appointment.Service.controller;

import com.Appointment.Service.model.Appointment;
import com.Appointment.Service.services.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppointmentController {

    private  static final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);
    @Autowired
    private AppointmentService appointmentService;

    // Add appointment
    @PostMapping("/appointment/add")
    public ResponseEntity<Appointment> add(@RequestBody Appointment appointment,
                                           @RequestHeader("X-Authenticated-User") String username,
                                           @RequestHeader("X-Authenticated-Role") String role){

        if (role .equals("customer")){
            LOGGER.info("Appointment added successfully");
            appointment.setUsername(username);
            return ResponseEntity.ok(appointmentService.bookAppointment(appointment));
        }
        LOGGER.warn("Only Customer can add appointment");
        return ResponseEntity.badRequest().build();
    }

    //Get all appointment
    @GetMapping("/appointment")
    public ResponseEntity<List<Appointment>> getAllAppointment(@RequestHeader("X-Authenticated-User") String username){
        if (appointmentService.getAppointment(username)!= null){
            LOGGER.info("Appointment for the user is fetched successfully");
            return ResponseEntity.ok(appointmentService.getAppointment(username));
        }
        LOGGER.warn("No Appointments found for the logged User");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/appointment/garageId/{garageId}")
    public ResponseEntity<List<Appointment>> getAppointmentByGarageId(@RequestHeader("X-Authenticated-Role") String role, @PathVariable Long garageId){
        if(role.equals("admin")||role.equals("mechanic")){
            return ResponseEntity.ok(appointmentService.getAppointmentByGarageId(garageId));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/appointment/all")
    public ResponseEntity<List<Appointment>> getAllAppointments(@RequestHeader("X-Authenticated-Role") String role) {
        LOGGER.debug("Received request to fetch all appointments with role: {}", role);

        if (!"ADMIN".equalsIgnoreCase(role)) {
            LOGGER.warn("Unauthorized access attempt to all appointments by role: {}", role);
            return ResponseEntity.badRequest().build();
        }
        List<Appointment> appointments = appointmentService.getAllAppointment();
        LOGGER.info("All appointments fetched successfully for admin.");
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/appointment/delete")
    public ResponseEntity<Void> deleteAppointment(Long id) {
        appointmentService.deleteAppointment(id);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }
}
