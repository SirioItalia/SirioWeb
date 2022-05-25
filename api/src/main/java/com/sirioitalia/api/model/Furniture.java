package com.sirioitalia.api.model;

import com.sirioitalia.api.embeddable.Dimension;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "furnitures")
public class Furniture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String label;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column(nullable = false)
    private String description;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "\"categoryId\"", nullable = false)
    private Category category;

    @NotNull
    @Embedded
    private Dimension dimension;

    @Positive
    private double weight;

    @OneToMany(mappedBy = "furniture", cascade = CascadeType.REMOVE)
    private Collection<Item> items = new ArrayList<>();
}
