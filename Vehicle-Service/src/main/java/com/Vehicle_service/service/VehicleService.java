package com.Vehicle_service.service;


import com.Vehicle_service.model.Vehicle;
import com.Vehicle_service.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepo vehicleRepo;

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepo.save(vehicle);
    }

    public List<Vehicle> getVehicleByOwner(String ownerUsername) {
        return vehicleRepo.findByOwnerUsername(ownerUsername);
    }

    public void deleteVehicle(Long id, String ownerUsername) {
        vehicleRepo.findById(id).filter(vehicle -> vehicle.getOwnerUsername().equals(ownerUsername))
                .ifPresentOrElse(vehicleRepo::delete, () -> {
                    throw new RuntimeException("Vehicle not found or unauthorized");
                });

    }

    public Vehicle updateVehicle(Long id, Vehicle updateVehicle, String ownerUsername){
        return vehicleRepo.findById(id).filter(vehicle -> vehicle.getOwnerUsername()
                .equals(ownerUsername)).map(vehicle -> {
            vehicle.setRegistration(updateVehicle.getRegistration());
            vehicle.setCompanyName(updateVehicle.getCompanyName());
            vehicle.setModelName(updateVehicle.getModelName());
            return vehicleRepo.save(vehicle);
        }).orElseThrow(()->new RuntimeException("Vehicle not found or unauthorized"));
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepo.findById(id);
    }

}
