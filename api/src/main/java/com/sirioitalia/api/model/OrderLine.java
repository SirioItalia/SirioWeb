package com.sirioitalia.api.model;

import com.sirioitalia.api.embeddable.OrderLinePK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "\"orderLines\"")
public class OrderLine {

    @EmbeddedId
    private OrderLinePK id = new OrderLinePK();

    @Positive
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "\"itemId\"")
    private Item item;

    @ManyToOne(optional = false)
    @MapsId("orderId")
    @JoinColumn(name = "\"orderId\"", nullable = false, insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private Order order;
}
