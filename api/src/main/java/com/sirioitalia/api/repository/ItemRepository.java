package com.sirioitalia.api.repository;

import com.sirioitalia.api.model.Item;
import com.sirioitalia.api.projection.ItemProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    @Query
    Iterable<ItemProjection> findBy();
}
