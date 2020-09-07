package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.CategoryDTO;
import com.pablozoani.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToDto(Category category);
}
