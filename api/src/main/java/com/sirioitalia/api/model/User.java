package com.sirioitalia.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sirioitalia.api.embeddable.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;


@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"firstName\"")
    private String firstName;

    @Column(name = "\"lastName\"")
    private String lastName;

    @Email
    @Column(unique = true)
    private String email;

    @Column(name = "\"passwordHash\"")
    private String passwordHash;

    @Column(name = "\"passwordSalt\"")
    private String passwordSalt;

    @Past
    @Column(name = "\"birthDate\"")
    private LocalDate birthDate;

    @Column(name = "\"phoneNumber\"", unique = true)
    private String phoneNumber;

    @NotNull
    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(name = "\"registrationDate\"", updatable = false)
    private LocalDateTime registrationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "\"roleId\"", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private Collection<Order> orders = new java.util.ArrayList<>();
}