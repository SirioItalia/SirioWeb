package com.sirioitalia.api.model;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String label;

    @Getter
    @Setter
    @Column(nullable = false)
    private String name;
}