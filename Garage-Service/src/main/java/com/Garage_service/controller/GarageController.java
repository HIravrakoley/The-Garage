package com.Garage_service.controller;

import com.Garage_service.client.AppointmentClient;
import com.Garage_service.dto.AppointmentRequest;
import com.Garage_service.dto.GarageRating;
import com.Garage_service.dto.VehicleResponse;
import com.Garage_service.model.Garage;
import com.Garage_service.service.GarageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GarageController {

    private  static final Logger LOGGER = LoggerFactory.getLogger(GarageController.class);
    @Autowired
    private GarageService garageService;

    // Test api
    @GetMapping("/garage/hello")
        public String hello(){
            return "Hello";
        }

        // Get all the garage(both admin and customer)
     @GetMapping("/garage")
    public ResponseEntity<List<Garage>> getAllGarage(){
        return ResponseEntity.ok(garageService.getallGarage());
     }

     //Get Garage by id (both admin and customer)
    @GetMapping("/garage/{id}")
    public ResponseEntity<Optional<Garage>> getGarageById(@PathVariable Long id){

        if(garageService.getGarageById(id)!=null){
            LOGGER.info("Garage is fetched successfully");
            return ResponseEntity.ok(garageService.getGarageById(id));
        }
        LOGGER.info("Garage is not found by the given ID");
        return ResponseEntity.noContent().build();
    }

    //Get all the user vehicle Details (only customer)
    @GetMapping("/garage/my-vehicle")
    public ResponseEntity<List<VehicleResponse>> getAllMyCars(@RequestHeader("X-Authenticated-User") String username,
    @RequestHeader("X-Authenticated-Role") String role){

    if(role.equals("customer")){
        LOGGER.info("Vehicle for the user is fetched successfully");
        return ResponseEntity.ok(garageService.getMyVehicle(username));
    }
    else
        LOGGER.info("Vehicle can be fetched only by customers");
        return ResponseEntity.badRequest().build();
    }

     // Add garages (only for admin)
     @PostMapping("/garage")
    public ResponseEntity<Garage> addGarage(@RequestBody Garage garage, @RequestHeader("X-Authenticated-Role") String role){

       if(role.equals("admin")) {
           LOGGER.info("Garage added successfully by admin");
           return ResponseEntity.ok(garageService.addGarage(garage));
       }
         LOGGER.info("Garage can only be added by admin");
       return ResponseEntity.badRequest().build();
    }

    // delete garage (only admin)
    @DeleteMapping("/garage/{id}")
    public ResponseEntity<Void> deleteGarage(@PathVariable Long id, @RequestHeader("X-Authenticated-Role") String role){

        if(role == "admin") {
            garageService.deleteGarage(id);
            LOGGER.info("Garage deleted successfully by admin");
        }
        LOGGER.info("Garage can only be deleted by admin");
        return ResponseEntity.noContent().build();
    }


@PostMapping("/garage/bookAppointment")
public ResponseEntity<String> bookAppointment(@RequestBody AppointmentRequest appointmentRequest,
                                              @RequestHeader("X-Authenticated-User") String username,
                                              @RequestHeader("X-Authenticated-Role") String role){
    if(role.equals("customer")) {
        appointmentRequest.setUsername(username);
        LOGGER.info("Customer has booked as appointment");
        return ResponseEntity.ok(garageService.bookAppointment(appointmentRequest,username,role));
    }
    LOGGER.warn("Appointment can only be booked by customer");
    return ResponseEntity.badRequest().build();
    }


//Ratings
//ADD Rating
@PostMapping("/garage/addRating")
    public ResponseEntity<GarageRating> addRating(@RequestBody GarageRating garageRating,
                                                  @RequestHeader("X-Authenticated-Role") String role,
                                                  @RequestHeader("X-Authenticated-User") String username ){
        garageRating.setUsername(username);
        if (role.equals("customer")){
        return ResponseEntity.ok(garageService.addRating(garageRating));
    }
        return ResponseEntity.badRequest().build();
    }
//VIEW ALl RATINGS
@GetMapping("/garage/viewRating")
    public ResponseEntity<List<GarageRating>> getAllRating(
            @RequestHeader("X-Authenticated-User") String username) {
    return ResponseEntity.ok(garageService.getAllRating());
}
//VIEW MY RATINGS
@GetMapping("/garage/myRating")
public ResponseEntity<List<GarageRating>> getMyRating(@RequestHeader("X-Authenticated-Role") String role, @RequestHeader("X-Authenticated-User") String username) {
        if(role.equals("customer")){
            return ResponseEntity.ok(garageService.getMyRating(username));
        }
        return ResponseEntity.badRequest().build();
}
//View Rating for particular garage
@GetMapping("/garage/viewRatingByGarageId/{garageId}")
    public ResponseEntity<List<GarageRating>> viewByGarageiD(@PathVariable Long garageId) {
            return ResponseEntity.ok(garageService.getByGarageId(garageId));
    }

}
