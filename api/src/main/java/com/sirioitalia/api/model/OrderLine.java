package com.sirioitalia.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sirioitalia.api.embeddable.OrderLinePK;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@DynamicUpdate
@Table(name = "\"orderLines\"")
public class OrderLine {

    @EmbeddedId
    @Getter
    private OrderLinePK id = new OrderLinePK();

    @Positive
    @Getter
    @Setter
    @Column(nullable = false)
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
    @MapsId("orderId")
    @JsonIgnore
    @JoinColumn(name = "\"orderId\"", nullable = false, insertable = false, updatable = false)
    private Order order;
}
