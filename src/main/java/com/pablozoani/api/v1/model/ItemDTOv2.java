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
public class ItemDTOv2 extends RepresentationModel<ItemDTOv2> {

    private Long id;

    private Integer quantity;

    private Long productId;
}
