package com.sirioitalia.api.embeddable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartPK implements Serializable {
    private static final long serialVersionUID = -3201792159113087356L;
    @Getter
    @Setter
    @Column(name = "\"itemId\"")
    private Long itemId;

    @Getter
    @Setter
    @Column(name = "\"userId\"")
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartPK)) return false;
        CartPK cartPK = (CartPK) o;
        return Objects.equals(itemId, cartPK.itemId) && Objects.equals(userId, cartPK.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, userId);
    }
}
