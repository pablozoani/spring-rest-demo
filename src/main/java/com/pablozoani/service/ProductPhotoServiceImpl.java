package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.ProductPhotoMapper;
import com.pablozoani.api.v1.model.ProductPhotoDTO;
import com.pablozoani.domain.ProductPhoto;
import com.pablozoani.exception.ResourceNotFoundException;
import com.pablozoani.repository.ProductPhotoRepository;
import com.pablozoani.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;

@Slf4j
@Service
public class ProductPhotoServiceImpl implements ProductPhotoService {

    private final ProductPhotoMapper productPhotoMapper;

    private final ProductPhotoRepository productPhotoRepository;

    private final ProductRepository productRepository;

    @Autowired
    public ProductPhotoServiceImpl(ProductPhotoMapper productPhotoMapper,
                                   ProductPhotoRepository productPhotoRepository,
                                   ProductRepository productRepository,
                                   EntityManager entityManager) {
        this.productPhotoMapper = productPhotoMapper;
        this.productPhotoRepository = productPhotoRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductPhotoDTO getProductPhotoByProductId(Long id) {
        ProductPhoto productPhoto = productPhotoRepository.findProductPhotoByProductId(id)
                .orElseThrow(ResourceNotFoundException::new);
        return productPhotoMapper.productPhotoToDto(productPhoto);
    }

    @Override
    public ProductPhotoDTO updateProductPhoto(Long productId, MultipartFile file) {
        return productRepository.findById(productId).map(product -> { // TODO. Try repo.getOne(Long)
            ProductPhoto productPhoto = product.getProductPhoto();
            if (productPhoto == null) {
                productPhoto = new ProductPhoto();
                product.setProductPhoto(productPhoto);
                productPhoto.setProduct(product);
            }
            try {
                productPhoto.setFileName(file.getOriginalFilename());
                productPhoto.setFileType(file.getContentType());
                productPhoto.setPhoto(file.getBytes());
            } catch (IOException exc) {
                log.error(exc.getMessage());
                return null;
            }
            product = productRepository.save(product);
            productPhoto = product.getProductPhoto();
            return productPhotoMapper.productPhotoToDto(productPhoto);
        }).orElseThrow(ResourceNotFoundException::new);
    }
}
