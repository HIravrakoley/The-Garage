package com.Garage_service.client;

import com.Garage_service.dto.GarageRating;
import com.Garage_service.dto.VehicleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="RATING-SERVICE")
public interface GarageRatingClient {

    @PostMapping("/rating/add")
    GarageRating addRating(@RequestBody GarageRating garageRating);

    @GetMapping("/rating/view")
    List<GarageRating> getAllRatings();

    @GetMapping("/rating/view-own")
    List<GarageRating> getMyOwnRating(@RequestHeader ("X-Authenticated-User") String username);

    @GetMapping("/rating/viewByGarageId/{garageId}")
    List<GarageRating> getByGarageId(@PathVariable Long garageId);
}
