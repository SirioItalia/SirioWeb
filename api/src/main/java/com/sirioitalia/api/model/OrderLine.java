package com.sirioitalia.api.model;

import com.sirioitalia.api.embeddable.OrderLinePK;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@DynamicUpdate
@Table(name = "\"orderLines\"")
public class OrderLine {

    @EmbeddedId
    private OrderLinePK id;

    @PositiveOrZero
    @Getter
    @Setter
    @Column(nullable = false)
    private Long quantity;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "\"itemId\"")
    private Item item;

    @Getter
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "\"orderId\"")
    private Order order;
}
