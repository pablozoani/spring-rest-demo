package com.pablozoani.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTOList {

    private List<ProductDTO> products;

    public static ProductDTOList of(List<ProductDTO> input) {
        return new ProductDTOList(input);
    }
}
