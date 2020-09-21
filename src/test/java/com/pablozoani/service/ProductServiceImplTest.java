package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.ProductMapper;
import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.domain.Product;
import com.pablozoani.exception.ResourceNotFoundException;
import com.pablozoani.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        // given
        ProductDTO productDTO = new ProductDTO(6L, "Apple Pack", 10.0);
        given(productRepository.save(any(Product.class))).willReturn(productMapper.dtoToProduct(productDTO));
        // when
        ProductDTO product = productService.createProduct(productDTO);
        // then
        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getPrice(), product.getPrice());
    }

    @Test
    void updateProduct() {
        // given
        Product product = new Product(7L, "Apple Pack", 11.5);
        given(productRepository.save(any())).willReturn(product);
        // when
        ProductDTO productDTO = productService.updateProduct(7L, productMapper.productToDto(product));
        // then
        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void patchProduct() {
        // given
        Product product = new Product(7L, "Apple Pack", 11.5);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);
        // when
        ProductDTO productDTO = productService.patchProduct(7L, productMapper.productToDto(product));
        // then
        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        verify(productRepository).findById(anyLong());
        verify(productRepository).save(any(Product.class));
        // and given
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        assertThrows(ResourceNotFoundException.class,
                () -> productService.patchProduct(7L, productMapper.productToDto(product)));
    }

    @Test
    void deleteProductById() {
        when(productRepository.existsById(anyLong())).thenReturn(true);
        productService.deleteProductById(11L);
        verify(productRepository).deleteById(anyLong());
    }

    @Test
    void getProductById() {
        // given
        Product product = new Product(13L, "Oranges Extra Acid", 6.3);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        // when
        ProductDTO productDTO = productService.getProductById(13L);
        // then
        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getPrice(), productDTO.getPrice());
        verify(productRepository).findById(anyLong());
    }
}