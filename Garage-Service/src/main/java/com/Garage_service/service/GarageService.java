package com.Garage_service.service;

import com.Garage_service.client.AppointmentClient;
import com.Garage_service.client.GarageRatingClient;
import com.Garage_service.client.VehicleClient;
import com.Garage_service.dto.AppointmentRequest;
import com.Garage_service.dto.GarageRating;
import com.Garage_service.dto.VehicleResponse;
import com.Garage_service.model.Garage;
import com.Garage_service.repository.GarageRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GarageService {

    @Autowired
    private GarageRepo garageRepo;

    @Autowired
    private AppointmentClient appointmentClient;

    @Autowired
    private VehicleClient vehicleClient;

    @Autowired
    private GarageRatingClient garageRatingClient;


    public List<Garage> getallGarage(){
        return garageRepo.findAll();
    }

    public Garage addGarage(Garage garage){
        return garageRepo.save(garage);
    }

    public Optional<Garage> getGarageById(Long id){
        return garageRepo.findById(id);
    }

    public void deleteGarage(Long id){
        garageRepo.deleteById(id);
    }
//    public Garage updateGarage(Garage garage,Long id){
//        Optional<Garage> g = garageRepo.findById(id);
//        if(g!=null){
//             return garageRepo.save(garage);
//        }
//        else
//    }

    //Get all the user vehicle using feing client
    @CircuitBreaker(name="VEHICLE-SERVICE",
            fallbackMethod = "fallbackFetchVehicle")
    public List<VehicleResponse> getMyVehicle(String username){
        return vehicleClient.getMyVehicle(username);
    }

    public List<VehicleResponse> fallbackFetchVehicle(String username,Throwable throwable){
        return new ArrayList<>();
    }


    //Book an appointment(Only Customer) using feing client
    @CircuitBreaker(name = "Appointment-Service",
    fallbackMethod = "fallbackBookAppointment")
    public String bookAppointment(AppointmentRequest request, String username, String role){
        return appointmentClient.bookAppointment(request,username,role);
    }
    public String fallbackBookAppointment(AppointmentRequest request,Throwable throwable){
        return "Appointment service is currently unavailable, Please try again later.";
    }

    // Add Rating using Feingclient

    public GarageRating addRating(GarageRating garageRating){
        return (GarageRating) garageRatingClient.addRating(garageRating);
    }
    public List<GarageRating> getAllRating(){
        return garageRatingClient.getAllRatings();
    }
    public List<GarageRating> getMyRating(String username){
        return garageRatingClient.getMyOwnRating(username);
    }

    public List<GarageRating> getByGarageId(Long garageId){
        return garageRatingClient.getByGarageId(garageId);
    }



}
