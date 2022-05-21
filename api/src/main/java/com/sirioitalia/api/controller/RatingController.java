package com.sirioitalia.api.controller;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Rating;
import com.sirioitalia.api.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(@Valid @RequestBody Rating ratingDetails)
            throws ResourceException {
        Rating createdRating = ratingService.createRating(ratingDetails);


        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable Long id, @Valid @RequestBody Rating ratingDetails) throws ResourceException {
        Rating updatedRating = ratingService.updateRating(id, ratingDetails);


        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRating(@PathVariable Long id) throws ResourceException {
        ratingService.deleteRating(id);


        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
