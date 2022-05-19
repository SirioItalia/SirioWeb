package com.sirioitalia.api.controller;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Furniture;
import com.sirioitalia.api.service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FurnitureController {
    @Autowired
    private FurnitureService furnitureService;

    @GetMapping("/furnitures")
    public List<Furniture> getFurnitures() {
        return furnitureService.getFurnitures();
    }

    @GetMapping("/furnitures/{id}")
    public ResponseEntity<Furniture> getFurnitureById(@PathVariable Long id) throws ResourceException {
        Furniture foundedFurniture = furnitureService.getFurnitureById(id);

        return new ResponseEntity<>(foundedFurniture, HttpStatus.FOUND);
    }

    @PostMapping("/furnitures")
    public ResponseEntity<Furniture> createFurniture(@Valid @RequestBody Furniture furnitureDetails)
        throws ResourceException {
        Furniture createdFurniture = furnitureService.createFurniture(furnitureDetails);

        return new ResponseEntity<>(createdFurniture, HttpStatus.CREATED);
    }

    @PutMapping("/furnitures/{id}")
    public ResponseEntity<Furniture> updateFurniture(@PathVariable Long id, @RequestBody Furniture furnitureDetails)
        throws ResourceException {
        Furniture updatedFurniture = furnitureService.updateFurniture(id, furnitureDetails);

        return new ResponseEntity<>(updatedFurniture, HttpStatus.OK);
    }

    @DeleteMapping("/furnitures/{id}")
    public ResponseEntity<HttpStatus> deleteFurniture(@PathVariable Long id) throws ResourceException {
        furnitureService.deleteFurniture(id);

        return ResponseEntity.ok(HttpStatus.GONE);
    }
}
