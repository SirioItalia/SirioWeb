package com.sirioitalia.api.model;

import com.sirioitalia.api.embeddable.Dimension;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
@Table(name = "furnitures")
public class Furniture implements Serializable{
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String label;

    @Getter
    @Setter
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(nullable = false)
    private String description;

    @Getter
    @Setter
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "\"categoryId\"", nullable = false)
    @Fetch(FetchMode.JOIN)
    private Category category;

    @Getter
    @Setter
    @Positive
    @Column
    private double price;

    @NotNull
    @Getter
    @Setter
    @Embedded
    private Dimension dimension;

    @Getter
    @Setter
    @Positive
    private double weight;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "furniture", cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.JOIN)
    private Collection<Item> items = new ArrayList<>();


}
