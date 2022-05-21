package com.sirioitalia.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sirioitalia.api.embeddable.RatingPK;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@DynamicUpdate
@Table(name = "\"itemRating\"")
public class Rating {
    @Getter
    @EmbeddedId
    private RatingPK id = new RatingPK();

    @Max(5)
    @Min(1)
    @Positive
    @Getter
    @Setter
    @Column(nullable = false, updatable = false)
    private Integer rating;


    @Getter
    @Setter
    @Column(updatable = false)
    private String comment;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "\"itemId\"")
    private Item item;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "\"userId\"")
    private User user;

    @Getter
    @CreationTimestamp
    @Column(name = "\"postedAt\"", updatable = false)
    private LocalDateTime postedAt;
}
