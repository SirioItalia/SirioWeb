package com.sirioitalia.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Getter
    @Column(name = "\"orderNumber\"", updatable = false)
    private Long orderNumber;


    @Getter
    @Column(name = "\"orderDate\"", updatable = false)
    @CreatedDate
    private LocalDateTime orderDate;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "\"userId\"", nullable = false)
    @Fetch(FetchMode.JOIN)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<OrderLine> orderLines = new ArrayList<>();
}

