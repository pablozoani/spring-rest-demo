package com.pablozoani.service;

import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.api.v1.model.ProductDTOList;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();
}
