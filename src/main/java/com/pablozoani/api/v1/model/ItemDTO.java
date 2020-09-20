package com.pablozoani.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(exclude = {"id"}, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO extends RepresentationModel<ItemDTO> {

    private Long id;

    private Integer quantity;

    private ProductDTO productDTO;
}
