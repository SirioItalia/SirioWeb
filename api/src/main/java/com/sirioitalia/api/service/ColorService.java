package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Color;
import com.sirioitalia.api.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ColorService {
    private ColorRepository colorRepository;

    public ColorService() {
        super();
    }


    @Autowired
    public ColorService(ColorRepository colorRepository) {
        super();
        this.colorRepository = colorRepository;
    }


    public List<Color> getColors() throws ResourceException {
        return (List<Color>) colorRepository.findAll();
    }

    public Color getColorById(Long colorId) throws ResourceException {
        Color color = colorRepository.findById(colorId)
                .orElseThrow(() -> new ResourceException("404", "Color not found", HttpStatus.NOT_FOUND));

        return color;
    }

    @Transactional
    public Color createColor(Color colorDetails) throws ResourceException {
        try {
            return colorRepository.save(colorDetails);
        } catch (Exception e) {
            throw new ResourceException("400", e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Transactional
    public Color updateColor(Long colorId, Color colorDetails) throws ResourceException {
        Color colorToUpdate = colorRepository.findById(colorId)
                .orElseThrow(() -> new ResourceException("404", "Color not found", HttpStatus.NOT_FOUND));

        colorToUpdate.setLabel(colorDetails.getLabel() == null
                ? colorToUpdate.getLabel()
                : colorDetails.getLabel());

        colorToUpdate.setHexadecimalCode(colorDetails.getHexadecimalCode() == null
                ? colorToUpdate.getHexadecimalCode()
                : colorDetails.getHexadecimalCode());

        return colorRepository.save(colorToUpdate);
    }

    @Transactional
    public void deleteColor(Long colorId) throws ResourceException {
        Color colorToDelete = colorRepository.findById(colorId)
                .orElseThrow(() -> new ResourceException("404", "Color not found", HttpStatus.NOT_FOUND));

        colorRepository.delete(colorToDelete);
    }
}
