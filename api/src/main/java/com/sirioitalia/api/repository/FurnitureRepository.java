package com.sirioitalia.api.repository;

import com.sirioitalia.api.model.Furniture;
import com.sirioitalia.api.projection.FurnitureProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FurnitureRepository extends CrudRepository<Furniture, Long> {
    Iterable<FurnitureProjection> findBy();

    Optional<FurnitureProjection> findProjectionById(Long furnitureId);
}
