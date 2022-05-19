package com.sirioitalia.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Cacheable
@Table(name = "images")
public class Image {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @NotBlank
    @NotEmpty
    @NotNull
    @Getter
    @Setter
    @ToString.Exclude
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "\"itemId\"", nullable = false, updatable = false)
    @Fetch(FetchMode.SELECT)
    @JsonIgnore
    private Item item;

}