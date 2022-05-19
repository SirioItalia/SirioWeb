package com.sirioitalia.api.repository;

import com.sirioitalia.api.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
