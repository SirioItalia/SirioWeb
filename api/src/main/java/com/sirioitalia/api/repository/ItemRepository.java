package com.sirioitalia.api.repository;

import com.sirioitalia.api.model.Item;
import com.sirioitalia.api.projection.ItemProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    @Query
    Iterable<ItemProjection.Full> findBy();

    @Query
    Optional<ItemProjection.Full> findProjectionById(Long itemId);
}
