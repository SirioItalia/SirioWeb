package com.sirioitalia.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.Collection;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty
    @NotNull
    private String reference;

    @PositiveOrZero
    @Column(name = "stock", nullable = false)
    private int stock;

    @Positive
    @Column
    private double price;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Image> images = new ArrayList<>();

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "\"furnitureId\"", nullable = false)
    private Furniture furniture;


    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "\"colorId\"", nullable = false)
    private Color color;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Rating> ratings = new ArrayList<>();
}
