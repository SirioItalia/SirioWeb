package com.sirioitalia.api.model;


import com.sirioitalia.api.embeddable.RatingPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "\"itemRating\"")
public class Rating {
    @EmbeddedId
    private RatingPK id = new RatingPK();

    @Max(5)
    @Min(1)
    @Positive
    @Column(nullable = false, updatable = false)
    private Integer rating;

    @Column(updatable = false)
    private String comment;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "\"itemId\"")
    private Item item;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "\"userId\"")
    private User user;

    @CreationTimestamp
    @Column(name = "\"postedAt\"", updatable = false)
    private LocalDateTime postedAt;
}
