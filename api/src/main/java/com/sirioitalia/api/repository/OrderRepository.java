package com.sirioitalia.api.repository;

import com.sirioitalia.api.model.Order;
import com.sirioitalia.api.projection.OrderProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query
    Iterable<OrderProjection> findBy();

    @Query
    Optional<OrderProjection> findProjectionById(Long orderId);
}