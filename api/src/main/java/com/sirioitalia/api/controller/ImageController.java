package com.sirioitalia.api.controller;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Image;
import com.sirioitalia.api.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public Iterable<Image> getImages() {
        return imageService.getImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable Long id) throws ResourceException {
        Image foundedImage = imageService.getImageById(id);


        return new ResponseEntity<>(foundedImage, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Image> createImage(@Valid @RequestBody Image imageDetails) throws ResourceException {
        Image createdImage = imageService.createImage(imageDetails);


        return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable Long id) throws ResourceException {
        imageService.deleteImage(id);


        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
