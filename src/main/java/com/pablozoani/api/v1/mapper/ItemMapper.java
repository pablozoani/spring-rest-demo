package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.ItemDTO;
import com.pablozoani.domain.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDTO itemToDto(Item item);

    Item dtoToItem(ItemDTO itemDTO);
}
