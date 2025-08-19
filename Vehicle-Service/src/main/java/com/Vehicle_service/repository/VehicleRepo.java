package com.Vehicle_service.repository;


import com.Vehicle_service.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle,Long> {
    List<Vehicle>findByOwnerUsername(String ownerUsername);

}
