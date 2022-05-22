package com.sirioitalia.api.model;

import com.sirioitalia.api.embeddable.CartPK;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Getter
    @EmbeddedId
    private CartPK cartPK = new CartPK();

    @Positive
    @Getter
    @Setter
    private Integer quantity;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "\"itemId\"")
    private Item item;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "\"userId\"")
    private User user;
}
