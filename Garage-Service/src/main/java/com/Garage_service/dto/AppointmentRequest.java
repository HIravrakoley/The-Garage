package com.Garage_service.dto;

import lombok.*;

import java.time.LocalDateTime;


@Data
public class AppointmentRequest {
    private Long id;
    private Long garageId;
    private Long vehicleId;
    private Long odometer;
    private LocalDateTime appointmentTime;
    private String username;  // Injected from API Gateway as header

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGarageId() {
        return garageId;
    }

    public void setGarageId(Long garageId) {
        this.garageId = garageId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
