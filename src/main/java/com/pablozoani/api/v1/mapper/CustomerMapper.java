package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.CustomerDTO;
import com.pablozoani.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToDto(Customer customer);

    Customer dtoToCustomer(CustomerDTO customerDTO);
}
