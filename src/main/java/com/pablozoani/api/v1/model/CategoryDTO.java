package com.pablozoani.api.v1.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"id", "products"}, callSuper = false)
@ToString(exclude = {"products"})
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO extends RepresentationModel<CategoryDTO> {

    private Long id;

    private String name;

    private List<ProductDTO> products = new ArrayList<>();

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
