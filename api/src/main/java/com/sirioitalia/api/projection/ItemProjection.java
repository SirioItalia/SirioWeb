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

    }

    interface Full {
        Long getId();

        Collection<ImageProjection> getImages();

        Collection<RatingProjection.FromItem> getRatings();

        ColorProjection getColor();
    }

}



