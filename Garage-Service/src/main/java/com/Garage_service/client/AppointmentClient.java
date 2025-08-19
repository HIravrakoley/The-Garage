package com.Garage_service.client;


import com.Garage_service.dto.AppointmentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "Appointment-Service")
public interface AppointmentClient {
    @PostMapping("/appointment/add")
    String bookAppointment(@RequestBody AppointmentRequest appointmentRequest,
                           @RequestHeader("X-Authenticated-User") String username,
    @RequestHeader("X-Authenticated-Role") String role);
}
