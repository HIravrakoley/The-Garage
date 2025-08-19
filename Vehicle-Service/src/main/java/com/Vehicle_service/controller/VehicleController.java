package com.Vehicle_service.controller;


import com.Vehicle_service.model.Vehicle;
import com.Vehicle_service.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class VehicleController {

    private  static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicle/hello")
    public String hello(){
        return "Hello";
    }

    @PostMapping("/vehicle")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle, @RequestHeader("X-Authenticated-User") String ownerUsername){
        vehicle.setOwnerUsername(ownerUsername);
        LOGGER.info("Vehicle added Successfully");
        return ResponseEntity.ok(vehicleService.addVehicle(vehicle));
    }

    @GetMapping("/vehicle/my-vehicle")
    public ResponseEntity<List<Vehicle>> getVehicles(@RequestHeader("X-Authenticated-User") String ownerUsername){
        return ResponseEntity.ok(vehicleService.getVehicleByOwner(ownerUsername));
    }

    @PutMapping("/vehicle/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle,@RequestHeader("X-Authenticated-User") String ownerUsername){
        return ResponseEntity.ok(vehicleService.updateVehicle(id,vehicle,ownerUsername));
    }

    @GetMapping("/vehicle/{id}")
    public ResponseEntity<Optional<Vehicle>> findVehicleByVehicle(@PathVariable Long id){
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @DeleteMapping("/vehicle/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id, @RequestHeader("X-Authenticated-User") String ownerUsername){
        vehicleService.deleteVehicle(id,ownerUsername);
        return ResponseEntity.noContent().build();
    }

}
