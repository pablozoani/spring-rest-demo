package com.pablozoani.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"photo", "product"})
@EqualsAndHashCode(exclude = "id")
public class ProductPhoto {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] photo;

    @OneToOne(optional = false)
    private Product product;
}
