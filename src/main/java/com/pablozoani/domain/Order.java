package com.pablozoani.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(exclude = {"id", "items", "customer"})
@ToString(exclude = {"customer", "items"})
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(optional = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = {PERSIST, REMOVE})
    private Set<Item> items = new HashSet<>();

    public Order(State state) {
        this.state = state;
    }
}
