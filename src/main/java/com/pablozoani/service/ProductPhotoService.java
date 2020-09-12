package com.pablozoani.service;

import com.pablozoani.api.v1.model.ProductPhotoDTO;

public interface ProductPhotoService {

    ProductPhotoDTO getProductPhotoById(Long id);

    ProductPhotoDTO createProductPhoto(ProductPhotoDTO productPhotoDTO);
}
