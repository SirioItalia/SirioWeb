package com.sirioitalia.api.projection;

import java.util.Collection;

public interface OrderProjection {
    Long getId();

    String getOrderReference();

    UserProjection.Short getUser();

    Collection<OrderLineProjection> getOrderLines();
}


