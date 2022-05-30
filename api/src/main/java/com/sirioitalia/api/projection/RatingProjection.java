package com.sirioitalia.api.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public interface RatingProjection {
    interface FromItem {
        @JsonProperty("publisher")
        UserProjection.Short getUser();

        Integer getRating();

        String getComment();

        LocalDateTime getPostedAt();
    }

    interface FromUser {
        Integer getRating();

        String getComment();

        LocalDateTime getPostedAt();

        ItemProjection.Short getItem();
    }
}

