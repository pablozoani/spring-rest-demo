package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.ProductPhotoMapper;
import com.pablozoani.api.v1.model.ProductPhotoDTO;
import com.pablozoani.repository.ProductPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPhotoServiceImpl implements ProductPhotoService {

    private final ProductPhotoMapper productPhotoMapper;

    private final ProductPhotoRepository productPhotoRepository;

    @Autowired
    public ProductPhotoServiceImpl(ProductPhotoMapper productPhotoMapper,
                                   ProductPhotoRepository productPhotoRepository) {
        this.productPhotoMapper = productPhotoMapper;
        this.productPhotoRepository = productPhotoRepository;
    }

    @Override
    public ProductPhotoDTO getProductPhotoById(Long id) {
        // TODO
        return null;
    }

    @Override
    public ProductPhotoDTO createProductPhoto(ProductPhotoDTO productPhotoDTO) {
        // TODO
        return null;
    }
}
