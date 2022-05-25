package com.sirioitalia.api.controller;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Cart;
import com.sirioitalia.api.projection.CartProjection;
import com.sirioitalia.api.projection.ItemProjection;
import com.sirioitalia.api.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public Iterable<CartProjection.Full> getCarts() {
        return cartService.getCarts();
    }

    @PostMapping
    public ResponseEntity<CartProjection> addItemInCart(@Valid @RequestBody Cart cartDetails) throws ResourceException {
        CartProjection createdItemInCart = cartService.addItemInCart(cartDetails);


        return new ResponseEntity<>(createdItemInCart, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/{itemId}")
    public Iterable<CartProjection> deleteItemInCart(@PathVariable Long userId, @PathVariable Long itemId) throws ResourceException {

        cartService.deleteItemInCart(userId, itemId);

        return cartService.getItemsInCartByUserId(userId);
    }
}
