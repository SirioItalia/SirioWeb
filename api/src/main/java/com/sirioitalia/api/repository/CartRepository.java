package com.sirioitalia.api.repository;

import com.sirioitalia.api.model.Cart;
import com.sirioitalia.api.projection.CartProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {

    @Query
    Iterable<CartProjection> findCartsProjectionByUserId(Long userId);

    @Query
    Iterable<Cart> findCartsByUserId(Long userId);

    @Query
    Optional<Cart> findCartByUserIdAndAndItemId(Long userId, Long itemId);
}
