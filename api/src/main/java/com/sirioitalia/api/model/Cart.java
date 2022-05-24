package com.sirioitalia.api.model;

import com.sirioitalia.api.embeddable.CartPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Cart {
    @EmbeddedId
    private CartPK cartPK = new CartPK();

    @Positive
    private Integer quantity;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "\"itemId\"")
    private Item item;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "\"userId\"")
    private User user;
}
