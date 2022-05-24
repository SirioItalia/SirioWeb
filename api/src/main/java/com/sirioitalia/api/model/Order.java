package com.sirioitalia.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"orderReference\"", updatable = false)
    private String orderReference;

    @Column(name = "\"orderDate\"", updatable = false)
    @CreationTimestamp
    private LocalDateTime orderDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "\"userId\"", nullable = false)
    @Fetch(FetchMode.JOIN)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private Collection<OrderLine> orderLines = new ArrayList<>();

}

