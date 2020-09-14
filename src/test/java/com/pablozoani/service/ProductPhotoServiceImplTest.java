package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.ProductPhotoMapper;
import com.pablozoani.api.v1.model.ProductPhotoDTO;
import com.pablozoani.domain.Product;
import com.pablozoani.domain.ProductPhoto;
import com.pablozoani.repository.ProductPhotoRepository;
import com.pablozoani.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class ProductPhotoServiceImplTest {

    @Mock
    ProductPhotoRepository productPhotoRepository;

    @Mock
    ProductRepository productRepository;

    @Spy
    ProductPhotoMapper productPhotoMapper = ProductPhotoMapper.INSTANCE;

    @InjectMocks
    ProductPhotoServiceImpl productPhotoService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void getProductPhotoById() {
        // given
        ProductPhoto productPhoto = new ProductPhoto(7L, "apple", "jpeg", new byte[]{'a', 'b'}, null);
        given(productPhotoRepository.findProductPhotoByProductId(anyLong()))
                .willReturn(Optional.of(productPhoto));
        // when
        ProductPhotoDTO productPhotoDTO = productPhotoService.getProductPhotoByProductId(7L);
        // then
        assertEquals(productPhoto.getId(), productPhotoDTO.getId());
        assertEquals(productPhoto.getFileName(), productPhotoDTO.getFileName());
        assertArrayEquals(productPhoto.getPhoto(), productPhotoDTO.getPhoto());
        verify(productPhotoRepository).findProductPhotoByProductId(anyLong());
    }

    @Test
    void updateProductPhoto() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "apple", "apple.jpeg", "image/jpeg", new byte[]{'a', 'b'});
        Product product = new Product(5L, "Apple Mix", 12.0);
        ProductPhoto productPhoto = new ProductPhoto(null, file.getOriginalFilename(),
                file.getContentType(), file.getBytes(), product);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);
        // when
        ProductPhotoDTO productPhotoDTO = productPhotoService.updateProductPhoto(5L, file);
        // then
        assertEquals(productPhoto.getId(), productPhotoDTO.getId());
        assertEquals(productPhoto.getFileName(), productPhotoDTO.getFileName());
        assertArrayEquals(productPhoto.getPhoto(), productPhotoDTO.getPhoto());
        assertEquals(productPhoto.getProduct(), product);
        verify(productRepository).findById(anyLong());
        verify(productRepository).save(any(Product.class));
    }
}