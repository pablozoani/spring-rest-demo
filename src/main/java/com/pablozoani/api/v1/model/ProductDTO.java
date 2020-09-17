package com.pablozoani.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(exclude = "id", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends RepresentationModel<ProductDTO> {

    private Long id;

    private String name;

    private Double price;
}
