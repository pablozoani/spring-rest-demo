package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.ProductPhotoDTO;
import com.pablozoani.domain.ProductPhoto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductPhotoMapper {

    ProductPhotoMapper INSTANCE = Mappers.getMapper(ProductPhotoMapper.class);

    ProductPhotoDTO productPhotoToDto(ProductPhoto productPhoto);

    ProductPhoto dtoToProductPhoto(ProductPhotoDTO productPhotoDTO);
}
