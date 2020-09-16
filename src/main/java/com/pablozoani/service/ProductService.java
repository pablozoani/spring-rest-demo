package com.pablozoani.service;

import com.pablozoani.api.v1.model.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    ProductDTO patchProduct(Long id, ProductDTO productDTO);

    void deleteProductById(Long id);

    ProductDTO getProductById(Long id);
}
