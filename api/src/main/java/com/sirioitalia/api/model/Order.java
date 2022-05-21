package com.sirioitalia.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "\"orderReference\"", updatable = false)
    private String orderReference;


    @Getter
    @Column(name = "\"orderDate\"", updatable = false)
    @CreationTimestamp
    private LocalDateTime orderDate;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "\"userId\"", nullable = false)
    @Fetch(FetchMode.JOIN)
    private User user;

    @Getter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private Collection<OrderLine> orderLines = new ArrayList<>();
}

