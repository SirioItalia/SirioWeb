package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Image;
import com.sirioitalia.api.model.Item;
import com.sirioitalia.api.projection.ItemProjection;
import com.sirioitalia.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ImageService imageService;

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


    public ItemProjection.Full createItem(Item item) throws ResourceException {
        try {
            Item createdItem = itemRepository.save(item);

            if (item.getImages() != null) {
                for (Image imageToAdd :
                        item.getImages()) {

                    imageToAdd.setItem(item);
                }
                imageService.createImages((List<Image>) item.getImages());
            }


            return (ItemProjection.Full) createdItem;
        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Transactional
    public Item updateItem(Long itemId, Item item) throws ResourceException {
        try {

            Item itemToUpdate = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ResourceException("404", "Item Not Found"));


            return itemRepository.save(itemToUpdate);

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