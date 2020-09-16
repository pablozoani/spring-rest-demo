package com.pablozoani.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@EqualsAndHashCode(exclude = {"id", "vendor", "productPhoto", "category"})
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private Double price;

    @OneToOne(fetch = LAZY, mappedBy = "product", cascade = {PERSIST, REMOVE})
    private ProductPhoto productPhoto;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private Category category;

    public Product(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
