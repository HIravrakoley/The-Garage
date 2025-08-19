package com.Garage_service.client;

import com.Garage_service.dto.VehicleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name="VEHICLE-SERVICE")
public interface VehicleClient {
    @GetMapping("/vehicle/my-vehicle")
    List<VehicleResponse>getMyVehicle(@RequestHeader ("X-Authenticated-User") String username);
}
