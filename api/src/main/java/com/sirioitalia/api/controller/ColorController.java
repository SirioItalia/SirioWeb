package com.sirioitalia.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Color;
import com.sirioitalia.api.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ColorController {
    @Autowired
    private ColorService colorService;


    @GetMapping("/colors")
    public List<Color> getColors() throws ResourceException {
        return colorService.getColors();
    }

    @GetMapping("/colors/{id}")
    public ResponseEntity<Color> getColor(@PathVariable Long id) throws ResourceException {
        Color color = colorService.getColorById(id);

        return new ResponseEntity<>(color, HttpStatus.FOUND);
    }

    @PostMapping("/colors")
    public ResponseEntity<Color> createColor(@Valid @RequestBody Color colorDetails) throws ResourceException {
        Color createdColor = colorService.createColor(colorDetails);

        return new ResponseEntity<>(createdColor, HttpStatus.CREATED);
    }

    @PutMapping("/colors/{id}")
    public ResponseEntity<Color> updateColor(@PathVariable Long id, @RequestBody Color colorDetails) throws ResourceException {
        Color updatedColor = colorService.updateColor(id, colorDetails);

        return new ResponseEntity<>(updatedColor, HttpStatus.OK);
    }

    @DeleteMapping("/colors/{id}")
    public ResponseEntity<HttpStatus> deleteColor(@PathVariable Long id) throws ResourceException {
        colorService.deleteColor(id);

        return ResponseEntity.ok().body(HttpStatus.GONE);
    }
}


