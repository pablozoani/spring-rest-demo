package com.pablozoani.service;

import com.pablozoani.api.v1.model.ProductPhotoDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProductPhotoService {

    ProductPhotoDTO getProductPhotoByProductId(Long id);

    ProductPhotoDTO updateProductPhoto(Long productId, MultipartFile file);
}
