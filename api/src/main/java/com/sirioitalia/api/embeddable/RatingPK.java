package com.sirioitalia.api.embeddable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class RatingPK implements Serializable {

    @Getter
    @Setter
    @Column(name = "\"itemId\"")
    private Long itemId;

    @Getter
    @Setter
    @Column(name = "\"userId\"")
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingPK)) return false;
        RatingPK ratingPK = (RatingPK) o;
        return Objects.equals(itemId, ratingPK.itemId) && Objects.equals(userId, ratingPK.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, userId);
    }
}