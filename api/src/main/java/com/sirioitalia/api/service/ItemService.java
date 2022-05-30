package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Image;
import com.sirioitalia.api.model.Item;
import com.sirioitalia.api.projection.ItemProjection;
import com.sirioitalia.api.repository.ItemRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ImageService imageService;
    private final ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

    @Autowired
    public ItemService(ItemRepository itemRepository, ImageService imageService) {
        this.itemRepository = itemRepository;
        this.imageService = imageService;
    }


    public Iterable<ItemProjection.Full> getItems() {
        return itemRepository.findBy();
    }


    public ItemProjection.Full getItemById(Long id) throws ResourceException {

        return itemRepository.findProjectionById(id)
                .orElseThrow(() -> new ResourceException("404", "Item Not Found"));
    }


    public ItemProjection.Short createItem(Item item) throws ResourceException {
        try {
            Item createdItem = itemRepository.save(item);
            String reference = createdItem.getReference() + createdItem.getId().toString() + Integer.toString(RandomUtils.nextInt(1000, 9999));
            System.out.println("reference:" + reference);
            System.out.println("idItem:" + createdItem.getId().toString());
            createdItem.setReference(reference);

            itemRepository.save(createdItem);

            if (item.getImages() != null) {

                for (Image imageToAdd :
                        item.getImages()) {
                    imageToAdd.setItem(item);
                    Image addedImage = imageService.createImage(imageToAdd);

                    String name = String.format("%s_%s", createdItem.getReference(), addedImage.getId().toString());
                    addedImage.setName(name);
                    imageService.createImage(addedImage);
                }
            }

            return projectionFactory.createProjection(ItemProjection.Short.class, createdItem);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Transactional
    public ItemProjection.Full updateItem(Long itemId, Item itemDetails) throws ResourceException {
        try {

            Item itemToUpdate = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ResourceException("404", "Item Not Found"));

            itemToUpdate.setPrice(Double.valueOf(itemDetails.getPrice()).equals(0.0)
                    ? itemToUpdate.getPrice()
                    : itemDetails.getPrice());

            itemToUpdate.setStock(Double.valueOf(itemDetails.getStock()).equals(0.0)
                    ? itemToUpdate.getStock()
                    : itemDetails.getStock());


            return projectionFactory.createProjection(ItemProjection.Full.class, itemRepository.save(itemToUpdate));

        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getMessage());
        }
    }


    @Transactional
    public void deleteItem(Long itemId) throws ResourceException {
        Item itemToDelete = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceException("404", "Item Not Found", HttpStatus.NOT_FOUND));

        itemRepository.delete(itemToDelete);
    }
}