package com.sirioitalia.api.embeddable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

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
}
