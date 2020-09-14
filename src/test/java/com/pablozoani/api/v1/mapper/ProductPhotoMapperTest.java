package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.ProductPhotoDTO;
import com.pablozoani.domain.ProductPhoto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductPhotoMapperTest {

    ProductPhotoMapper productPhotoMapper = ProductPhotoMapper.INSTANCE;

    @BeforeEach
    void setUp() {
    }

    @Test
    void productPhotoToDto() {
        // given
        ProductPhoto productPhoto = new ProductPhoto(7L, "apple", "jpeg", new byte[]{'a', 'b'}, null);
        // when
        ProductPhotoDTO productPhotoDTO = productPhotoMapper.productPhotoToDto(productPhoto);
        // then
        assertEquals(productPhoto.getId(), productPhotoDTO.getId());
        assertEquals(productPhoto.getFileName(), productPhotoDTO.getFileName());
        assertEquals(productPhoto.getFileType(), productPhotoDTO.getFileType());
        assertArrayEquals(productPhoto.getPhoto(), productPhotoDTO.getPhoto());
    }

    @Test
    void dtoToProductPhoto() {
        // given
        ProductPhotoDTO productPhotoDTO = new ProductPhotoDTO(7L, "apple", "jpeg", new byte[]{'a', 'b'});
        // when
        ProductPhoto productPhoto = productPhotoMapper.dtoToProductPhoto(productPhotoDTO);
        // then
        assertEquals(productPhotoDTO.getId(), productPhoto.getId());
        assertEquals(productPhotoDTO.getFileName(), productPhoto.getFileName());
        assertEquals(productPhotoDTO.getFileType(), productPhoto.getFileType());
        assertArrayEquals(productPhotoDTO.getPhoto(), productPhoto.getPhoto());
    }
}