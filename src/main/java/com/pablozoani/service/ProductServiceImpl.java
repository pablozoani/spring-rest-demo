package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.ProductMapper;
import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.domain.Product;
import com.pablozoani.exception.ResourceNotFoundException;
import com.pablozoani.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::productToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.dtoToProduct(productDTO);
        product = productRepository.save(product);
        return productMapper.productToDto(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        productDTO.setId(id);
        Product product = productMapper.dtoToProduct(productDTO);
        product = productRepository.save(product);
        return productMapper.productToDto(product);
    }

    @Override
    public ProductDTO patchProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id).map(product -> {
            if (productDTO.getName() != null) product.setName(productDTO.getName());
            if (productDTO.getPrice() != null) product.setPrice(productDTO.getPrice());
            product = productRepository.save(product);
            return productMapper.productToDto(product);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return productMapper.productToDto(product);
    }
}
