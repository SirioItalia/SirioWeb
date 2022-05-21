package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Furniture;
import com.sirioitalia.api.model.Item;
import com.sirioitalia.api.repository.FurnitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FurnitureService {
    private final FurnitureRepository furnitureRepository;
    private final ItemService itemService;

    @Autowired
    public FurnitureService(FurnitureRepository furnitureRepository, ItemService itemService) {
        super();
        this.furnitureRepository = furnitureRepository;
        this.itemService = itemService;
    }


    public Iterable<Furniture> getFurnitures() {
        return furnitureRepository.findAll();
    }


    public Furniture getFurnitureById(Long furnitureId) throws ResourceException {
        return furnitureRepository.findById(furnitureId)
                .orElseThrow(() -> new ResourceException("404", "Furniture not found", HttpStatus.NOT_FOUND));
    }


    @Transactional
    public Furniture createFurniture(Furniture furnitureDetails) throws ResourceException {
        try {
            Furniture addedFurniture = furnitureRepository.save(furnitureDetails);

            for (Item itemToAdd :
                    furnitureDetails.getItems()) {
                itemToAdd.setFurniture(addedFurniture);
                itemService.createItem(itemToAdd);
            }

            return addedFurniture;
        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getCause(), HttpStatus.CONFLICT);
        }
    }


    @Transactional
    public Furniture updateFurniture(Long furnitureId, Furniture furnitureDetails) throws ResourceException {
        Furniture furnitureToUpdate = furnitureRepository.findById(furnitureId)
                .orElseThrow(() -> new ResourceException("404", "Furniture not found", HttpStatus.NOT_FOUND));

        furnitureToUpdate.setLabel(furnitureDetails.getLabel() == null
                ? furnitureToUpdate.getLabel()
                : furnitureDetails.getLabel());

        furnitureToUpdate.setDescription(furnitureDetails.getDescription() == null
                ? furnitureToUpdate.getDescription()
                : furnitureDetails.getDescription());

        furnitureToUpdate.setPrice(Double.valueOf(furnitureDetails.getPrice()).equals(0.0)
                ? furnitureToUpdate.getPrice()
                : furnitureDetails.getPrice());

        furnitureToUpdate.setWeight(Double.valueOf(furnitureDetails.getWeight()).equals(0.0)
                ? furnitureToUpdate.getWeight()
                : furnitureDetails.getWeight());

        furnitureToUpdate.setCategory(furnitureDetails.getCategory() == null
                ? furnitureToUpdate.getCategory()
                : furnitureDetails.getCategory());

        furnitureToUpdate.setDimension(furnitureDetails.getDimension() == null
                ? furnitureToUpdate.getDimension()
                : furnitureDetails.getDimension());

        return furnitureRepository.save(furnitureToUpdate);
    }


    @Transactional
    public void deleteFurniture(Long furnitureId) throws ResourceException {
        Furniture furnitureToDelete = furnitureRepository.findById(furnitureId)
                .orElseThrow(() -> new ResourceException("404", "Item Not Found", HttpStatus.NOT_FOUND));

        furnitureRepository.delete(furnitureToDelete);
    }


}
