package com.sirioitalia.api.projection;

import java.util.Collection;

public interface OrderProjection {
    interface WithUserInfo {
        Long getId();

        String getOrderReference();

        UserProjection.Short getUser();

        Collection<OrderLineProjection> getOrderLines();
    }

    interface WithoutUserInfo {
        Long getId();

        String getOrderReference();

        Collection<OrderLineProjection> getOrderLines();
    }

}


