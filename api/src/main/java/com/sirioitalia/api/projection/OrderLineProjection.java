package com.sirioitalia.api.projection;

public interface OrderLineProjection {

    Integer getQuantity();

    ItemProjection.Short getItem();
}