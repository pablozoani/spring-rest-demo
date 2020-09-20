package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.OrderDTO;
import com.pablozoani.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO orderToDto(Order order);

    Order dtoToOrder(OrderDTO orderDTO);
}
