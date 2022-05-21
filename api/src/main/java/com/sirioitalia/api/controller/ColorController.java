package com.sirioitalia.api.controller;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Color;
import com.sirioitalia.api.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/colors")
public class ColorController {
    private final ColorService colorService;

    @Autowired
    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping
    public List<Color> getColors() throws ResourceException {
        return colorService.getColors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> getColor(@PathVariable Long id) throws ResourceException {
        Color color = colorService.getColorById(id);

        return new ResponseEntity<>(color, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Color> createColor(@Valid @RequestBody Color colorDetails) throws ResourceException {
        Color createdColor = colorService.createColor(colorDetails);

        return new ResponseEntity<>(createdColor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> updateColor(@PathVariable Long id, @RequestBody Color colorDetails) throws ResourceException {
        Color updatedColor = colorService.updateColor(id, colorDetails);

        return new ResponseEntity<>(updatedColor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteColor(@PathVariable Long id) throws ResourceException {
        colorService.deleteColor(id);

        return ResponseEntity.ok().body(HttpStatus.GONE);
    }
}


