package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.domain.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    ProductMapper productMapper = ProductMapper.INSTANCE;

    @Test
    void productToDto() {
        // given
        Product product = new Product(8L, "Apple Pack", 10.0);
        // when
        ProductDTO productDTO = productMapper.productToDto(product);
        // then
        assertNotNull(productDTO);
        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getPrice(), productDTO.getPrice());
    }

    @Test
    void dtoToProduct() {
        // given
        ProductDTO productDTO = new ProductDTO(8L, "Apple Pack", 10.0);
        // when
        Product product = productMapper.dtoToProduct(productDTO);
        // then
        assertNotNull(productDTO);
        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getPrice(), product.getPrice());
    }
}