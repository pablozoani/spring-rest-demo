package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.ProductMapper;
import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.domain.Product;
import com.pablozoani.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    ProductMapper productMapper = ProductMapper.INSTANCE;

    ProductService productService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        productService = new ProductServiceImpl(productMapper, productRepository);
    }

    @Test
    void getAllProducts() {
        // given
        Product apple = new Product(1L, "Apple Pack", 10.0),
                strawberry = new Product(2L, "Strawberry Box", 8.0);
        List<Product> products = asList(apple, strawberry);
        given(productRepository.findAll()).willReturn(products);
        // when
        List<ProductDTO> allProducts = productService.getAllProducts();
        // then
        assertEquals(2, allProducts.size());
        allProducts.forEach(Assertions::assertNotNull);
    }

    @Test
    void createProduct() {
        // TODO
    }

    @Test
    void updateProduct() {
        // TODO
    }

    @Test
    void patchProduct() {
        // TODO
    }

    @Test
    void deleteProductById() {
        // TODO
    }

    @Test
    void getProductById() {
        // TODO
    }
}