package com.Rating.repositories;

import com.Rating.model.Rating;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating,Long> {
    List<Rating> findByUsername(String username);
    List<Rating> findByGarageId(Long garageId);
}
