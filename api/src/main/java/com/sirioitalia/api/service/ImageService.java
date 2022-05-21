package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Image;
import com.sirioitalia.api.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    public Iterable<Image> getImages() throws ResourceException {

        return imageRepository.getImages();
    }


    public Image getImageById(Long imageId) throws ResourceException {

        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceException("404", "Image not found", HttpStatus.NOT_FOUND));
    }


    @Transactional
    public Image createImage(Image imageDetails) throws ResourceException {
        try {

            return imageRepository.save(imageDetails);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getCause(), HttpStatus.CONFLICT);
        }
    }


    @Transactional
    public Iterable<Image> createImages(List<Image> imagesDetails) throws ResourceException {
        try {
            return imageRepository.saveAll(imagesDetails);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getCause(), HttpStatus.CONFLICT);
        }
    }


    @Transactional
    public void deleteImage(Long imageId) throws ResourceException {
        Image imageToDelete = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceException("404", "Image not found", HttpStatus.NOT_FOUND));

        imageRepository.delete(imageToDelete);
    }
}
