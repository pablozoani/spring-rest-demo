package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.CategoryMapper;
import com.pablozoani.api.v1.model.CategoryDTO;
import com.pablozoani.domain.Category;
import com.pablozoani.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CategoryServiceImplTest {

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
    }

    @Test
    void getAllCategories() {
        // given
        Category fruits = new Category(1L, "Fruits"),
                nuts = new Category(2L, "Nuts"),
                fresh = new Category(3L, "Fresh");
        List<Category> categoryList = asList(fruits, nuts, fresh);
        // when
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
        // then
        assertEquals(categoryList.size(), categoryDTOList.size());
        categoryDTOList.forEach(Assertions::assertNotNull);
    }

    @Test
    void getCategoryByName() {
        // given
        Category fruits = new Category(1L, "Fruits");
        // when
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(fruits));
        CategoryDTO categoryDTO = categoryService.getCategoryByName("Fruits");
        // then
        assertNotNull(categoryDTO);
        assertEquals(fruits.getId(), categoryDTO.getId());
        assertEquals(fruits.getName(), categoryDTO.getName());
    }
}