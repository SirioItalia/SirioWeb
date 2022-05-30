package com.sirioitalia.api.projection;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;

public interface ItemProjection {
    interface Short {
        Long getId();

        ColorProjection getColor();

        @JsonProperty("furnitureId")
        @Value("#{target.getFurniture().getId()}")
        String getFurniture();

        @JsonProperty("label")
        @Value("#{target.getFurniture().getLabel()}")
        String getLabel();

        @JsonProperty("description")
        @Value("#{target.getFurniture().getDescription()}")
        String getDescription();

        @JsonProperty("category")
        @Value("#{target.getFurniture().getCategory().getLabel()}")
        String getCategory();

        Double getPrice();
        String getReference();
    }

    interface Full {
        Long getId();

        Collection<ImageProjection> getImages();

        Collection<RatingProjection.FromItem> getRatings();

        ColorProjection getColor();

        Double getPrice();

        String getReference();
    }

}



