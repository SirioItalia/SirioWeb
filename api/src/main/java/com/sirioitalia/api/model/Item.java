package com.sirioitalia.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@DynamicUpdate
@Table(name = "items")
public class Item implements Serializable{

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    @Getter
    @Setter
    @Column(unique = true, updatable = false)
    @NotEmpty
    @NotNull
    private String reference;

    @PositiveOrZero
    @Getter
    @Setter
    @Column(name = "stock", nullable = false)
    private int stock;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private Collection<Image> images = new ArrayList<>();

    @Getter
    @Setter
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "\"furnitureId\"", nullable = false)
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Furniture furniture;

    @Getter
    @Setter
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "\"colorId\"", nullable = false)
    @Fetch(FetchMode.JOIN)
    private Color color;
}
