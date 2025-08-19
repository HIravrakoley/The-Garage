package com.Rating.controller;

import com.Rating.model.Rating;
import com.Rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/rating/add")
    public ResponseEntity<Rating> addRating(@RequestBody Rating rating){
           return ResponseEntity.ok(ratingService.addRating(rating));
    }

    @GetMapping("/rating/view")
    public ResponseEntity<List<Rating>> getAllRating() {
          return ResponseEntity.ok(ratingService.viewRating());
    }

    @GetMapping("/rating/view-own")
    public ResponseEntity<List<Rating>> getMyRating( @RequestHeader("X-Authenticated-User") String ownerUsername){
            return ResponseEntity.ok(ratingService.viewMyOwnRating(ownerUsername));
    }

    @GetMapping("/rating/viewByGarageId/{id}")
    public ResponseEntity<List<Rating>> getMyRating(@PathVariable Long id ){
        return ResponseEntity.ok(ratingService.viewByGarageId(id));
    }
}
