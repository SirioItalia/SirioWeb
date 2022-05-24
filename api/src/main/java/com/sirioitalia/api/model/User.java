package com.sirioitalia.api.model;

import com.sirioitalia.api.embeddable.Address;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User implements Serializable {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "\"firstName\"")
    private String firstName;

    @Getter
    @Setter
    @Column(name = "\"lastName\"")
    private String lastName;

    @Email
    @Getter
    @Setter
    @Column(unique = true)
    private String email;

    @Getter
    @Setter
    @Column(name = "\"passwordHash\"")
    private String passwordHash;

    @Getter
    @Setter
    @Column(name = "\"passwordSalt\"")
    private String passwordSalt;

    @Past
    @Getter
    @Setter
    @Column(name = "\"birthdate\"")
    private LocalDate birthDate;

    @Getter
    @Setter
    @Column(name = "\"phoneNumber\"", unique = true)
    private String phoneNumber;

    @NotNull
    @Getter
    @Setter
    @Embedded
    private Address address;

    @Getter
    @CreationTimestamp
    @Column(name = "\"registrationDate\"", updatable = false)
    private LocalDateTime registrationDate;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "\"roleId\"", nullable = false)
    private Role role;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private Set<Order> orders = new LinkedHashSet<>();
}