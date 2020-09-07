package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.CategoryDTO;
import com.pablozoani.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToDto() {
        // given
        Category category = new Category(13L, "Food");
        // when
        CategoryDTO categoryDTO = categoryMapper.categoryToDto(category);
        // then
        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());
    }
}