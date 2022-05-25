package com.sirioitalia.api.projection;

import com.sirioitalia.api.embeddable.CartPK;

public interface CartProjection {
    interface Short {
        ItemProjection getItem();
    }

    interface Full {
        CartPK getCartPK();

        ItemProjection.Short getItem();

        UserProjection.Short getUser();
    }
}
