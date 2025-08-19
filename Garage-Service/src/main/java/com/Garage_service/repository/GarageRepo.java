package com.Garage_service.repository;

import com.Garage_service.model.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarageRepo extends JpaRepository<Garage,Long> {
}
