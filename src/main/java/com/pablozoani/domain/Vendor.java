package com.pablozoani.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

@Data
@EqualsAndHashCode(exclude = {"id", "products"})
@ToString(exclude = {"products"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = {PERSIST, REMOVE}, mappedBy = "vendor")
    private Set<Product> products = new HashSet<>();

    public Vendor(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
