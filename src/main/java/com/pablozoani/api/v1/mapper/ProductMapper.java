package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO productToDto(Product product);

    Product dtoToProduct(ProductDTO productDTO);
}
