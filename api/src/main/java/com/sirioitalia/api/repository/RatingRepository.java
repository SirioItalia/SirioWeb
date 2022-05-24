package com.sirioitalia.api.repository;

import com.sirioitalia.api.model.Rating;
import com.sirioitalia.api.projection.RatingProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    @Query("select rtg from Rating rtg where rtg.item.id = :itemId")
    Iterable<Rating> findByItemId(@Param("itemId") Long itemId);

    @Query
    Iterable<RatingProjection.FromItem> findBy();
}
