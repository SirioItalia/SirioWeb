package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Cart;
import com.sirioitalia.api.projection.CartProjection;
import com.sirioitalia.api.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

    public Iterable<CartProjection.Full> getCarts() {
        return cartRepository.findBy();
    }

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Iterable<CartProjection> getItemsInCartByUserId(Long userId) {
        return cartRepository.findCartsProjectionByUserId(userId);
    }

    public CartProjection getItemInCartByUserId(Long userId, Long itemId) throws ResourceException {
        Cart foundedItemInCartProjection = cartRepository.findCartByUserIdAndAndItemId(userId, itemId)
                .orElseThrow(() -> new ResourceException("404", "Item in cart not found", HttpStatus.NOT_FOUND));

        return (CartProjection) foundedItemInCartProjection;
    }

    @Transactional
    public CartProjection addItemInCart(Cart cartDetails) throws ResourceException {
        try {

            cartDetails.setQuantity(1);
            Cart createdItem = cartRepository.save(cartDetails);

            return projectionFactory.createProjection(CartProjection.class, createdItem);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getCause(), HttpStatus.CONFLICT);
        }
    }

    @Transactional
    public CartProjection updateItemInCart(Cart cartDetails) throws ResourceException {
        try {
            Cart updatedItemInCart = cartRepository.save(cartDetails);

            return projectionFactory.createProjection(CartProjection.class, updatedItemInCart);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getCause(), HttpStatus.CONFLICT);
        }
    }

    @Transactional
    public void deleteItemInCart(Long userId, Long itemId) throws ResourceException {
        Cart cartToDelete = cartRepository.findCartByUserIdAndAndItemId(userId, itemId)
                .orElseThrow(() -> new ResourceException("404", "Item in cart not found", HttpStatus.NOT_FOUND));


        cartRepository.delete(cartToDelete);
    }

    @Transactional
    public void deleteAllCartByUserId(Long userId) throws ResourceException {
        Iterable<Cart> cartToDelete = cartRepository.findCartsByUserId(userId);

        cartRepository.deleteAll(cartToDelete);
    }


}
