package com.sirioitalia.api.repository;

import com.sirioitalia.api.model.Furniture;
import org.springframework.data.repository.CrudRepository;

public interface FurnitureRepository extends CrudRepository<Furniture, Long> {
}
