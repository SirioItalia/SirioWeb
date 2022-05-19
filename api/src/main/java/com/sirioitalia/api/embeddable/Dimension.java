package com.sirioitalia.api.embeddable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Positive;

@Embeddable
public class Dimension {
    @Getter
    @Setter
    @Positive
    @Column
    private double width;

    @Getter
    @Setter
    @Positive
    @Column(nullable = false)
    private double length;

    @Getter
    @Setter
    @Positive
    @Column(nullable = false)
    private double height;
}
