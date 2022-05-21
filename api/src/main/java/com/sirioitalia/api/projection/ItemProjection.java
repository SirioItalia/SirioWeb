package com.sirioitalia.api.projection;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ItemProjection {
    Long getId();

    Collection<ImageProjection> getImages();

    Collection<RatingProjection> getRatings();
}

interface ColorProjection {
    Long getId();

    String getLabel();

    String getHexadecimalCode();
}

interface ImageProjection {
    Long getId();

    String getName();
}

interface RatingProjection {
    @JsonProperty("publisher")
    UserProjection getUser();

    Integer getRating();

    String getComment();

    LocalDateTime getPostedAt();

    interface UserProjection {
        Long getId();

        String getFirstName();

        String getLastName();
    }
}