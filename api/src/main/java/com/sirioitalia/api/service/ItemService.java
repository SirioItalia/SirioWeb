package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Color;
import com.sirioitalia.api.model.Image;
import com.sirioitalia.api.model.Item;
import com.sirioitalia.api.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private ItemRepository itemRepository;
    private ImageService imageService;
    private ColorService colorService;

    public ItemService() {
        super();
    }


    @Autowired
    public ItemService(ItemRepository itemRepository, ImageService imageService, ColorService colorService) {
        super();
        this.itemRepository = itemRepository;
        this.imageService = imageService;
        this.colorService = colorService;
    }


    public List<Item> getItems() {
        return (List<Item>) itemRepository.findAll();
    }


    public Item getItemById(Long id) throws ResourceException {
        Item itemFound = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceException("404", "Item Not Found"));


        return itemFound;
    }


    public Item createItem(Item item) throws ResourceException {
        try {
            Item createdItem = itemRepository.save(item);

            if (item.getImages() != null ) {
                for (Image imageToAdd:
                     item.getImages()) {

                    imageToAdd.setItem(item);
                }
                imageService.createImages((List<Image>) item.getImages());
            }


            return createdItem;
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