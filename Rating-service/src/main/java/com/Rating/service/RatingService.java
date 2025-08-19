package com.Rating.service;

import com.Rating.model.Rating;
import com.Rating.repositories.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepo ratingRepo;


    public Rating addRating(Rating rating){
        return ratingRepo.save(rating);
    }

    public List<Rating> viewRating(){
        return ratingRepo.findAll();
    }

    public List<Rating> viewMyOwnRating(String ownerUsername){
        return ratingRepo.findByUsername(ownerUsername);
    }

    public List<Rating> viewByGarageId(Long garageId){
        return ratingRepo.findByGarageId(garageId);
    }
}
