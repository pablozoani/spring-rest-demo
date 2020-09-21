package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.CategoryMapper;
import com.pablozoani.api.v1.model.CategoryDTO;
import com.pablozoani.domain.Category;
import com.pablozoani.domain.Product;
import com.pablozoani.exception.ResourceNotFoundException;
import com.pablozoani.repository.CategoryRepository;
import com.pablozoani.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final ProductRepository productRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper,
                               ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(ResourceNotFoundException::new);
        return categoryMapper.categoryToDto(category);
    }

    @Override
    public CategoryDTO addProductToCategoryByProductIdAndCategoryId(Long categoryId, Long productId) {
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found."));
                    category.getProducts().add(product);
                    product.setCategory(category);
                    category = categoryRepository.save(category);
                    return categoryMapper.categoryToDto(category);
                }).orElseThrow(() -> new ResourceNotFoundException("Category " + categoryId + " not found."));
    }
}
