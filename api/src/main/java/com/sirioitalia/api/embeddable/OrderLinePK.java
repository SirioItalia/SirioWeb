package com.sirioitalia.api.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class OrderLinePK implements Serializable {

    @Column(name = "\"itemId\"")
    private Long itemId;

    @Column(name = "\"orderId\"")
    private Long orderId;
}