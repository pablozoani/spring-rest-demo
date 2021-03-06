package com.pablozoani.service;

import com.pablozoani.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);

    CategoryDTO addProductToCategoryByProductIdAndCategoryId(Long categoryId, Long productId);
}
