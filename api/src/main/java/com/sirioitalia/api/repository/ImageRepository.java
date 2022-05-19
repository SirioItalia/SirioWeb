package com.sirioitalia.api.repository;

import com.sirioitalia.api.model.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    @Query("select img.id, img.name, img.item.id from Image img")
    List<Image> getImages();
}
