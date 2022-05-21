package com.sirioitalia.api.controller;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Furniture;
import com.sirioitalia.api.projection.FurnitureProjection;
import com.sirioitalia.api.service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/furnitures")
public class FurnitureController {
    private final FurnitureService furnitureService;

    @Autowired
    public FurnitureController(FurnitureService furnitureService) {
        this.furnitureService = furnitureService;
    }

    @GetMapping
    public Iterable<FurnitureProjection> getFurnitures() {
        return furnitureService.getFurnitures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FurnitureProjection> getFurnitureById(@PathVariable Long id) throws ResourceException {
        FurnitureProjection foundedFurniture = furnitureService.getFurnitureById(id);

        return new ResponseEntity<>(foundedFurniture, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<FurnitureProjection> createFurniture(@Valid @RequestBody Furniture furnitureDetails)
            throws ResourceException {
        FurnitureProjection createdFurniture = furnitureService.createFurniture(furnitureDetails);

        return new ResponseEntity<>(createdFurniture, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Furniture> updateFurniture(@PathVariable Long id, @RequestBody Furniture furnitureDetails)
            throws ResourceException {
        Furniture updatedFurniture = furnitureService.updateFurniture(id, furnitureDetails);

        return new ResponseEntity<>(updatedFurniture, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFurniture(@PathVariable Long id) throws ResourceException {
        furnitureService.deleteFurniture(id);

        return ResponseEntity.ok(HttpStatus.GONE);
    }
}
