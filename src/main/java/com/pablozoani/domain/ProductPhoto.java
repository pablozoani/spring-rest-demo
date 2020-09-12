package com.pablozoani.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "id")
public class ProductPhoto {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Lob
    private Byte[] photo;
}
