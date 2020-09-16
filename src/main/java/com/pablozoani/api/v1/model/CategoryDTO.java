package com.pablozoani.api.v1.model;

import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"id", "products"})
@ToString(exclude = {"products"})
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    private String name;

    private List<ProductDTO> products;

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
