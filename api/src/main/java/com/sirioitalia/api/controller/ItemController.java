package com.sirioitalia.api.controller;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Item;
import com.sirioitalia.api.projection.ItemProjection;
import com.sirioitalia.api.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public Iterable<ItemProjection.Full> getItems() {
        return itemService.getItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemProjection.Full> getItemById(@PathVariable Long id) throws ResourceException {
        ItemProjection.Full item = itemService.getItemById(id);


        return new ResponseEntity<>(item, HttpStatus.FOUND);
    }


    @PostMapping
    @Transactional
    public ResponseEntity<ItemProjection.Short> createItem(@Valid @RequestBody Item item) throws ResourceException {
        ItemProjection.Short createdItem = itemService.createItem(item);


        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemProjection.Full> updateItem(@RequestBody Item itemDetails, @PathVariable Long id) throws ResourceException {
        ItemProjection.Full updatedItem = itemService.updateItem(id, itemDetails);

        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) throws ResourceException {
        itemService.deleteItem(id);

        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
