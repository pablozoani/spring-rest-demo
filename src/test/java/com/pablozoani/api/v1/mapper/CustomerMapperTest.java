package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.CustomerDTO;
import com.pablozoani.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerMapperTest {

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToDto() {
        // given
        Customer customer = new Customer(1L, "Pablo", "Zoani");
        // when
        CustomerDTO customerDTO = customerMapper.customerToDto(customer);
        // then
        assertNotNull(customerDTO);
        assertEquals(customer.getId(), customerDTO.getId());
        assertEquals(customer.getFirstName(), customerDTO.getFirstName());
        assertEquals(customer.getLastName(), customerDTO.getLastName());
    }

    @Test
    void dtoToCustomer() {
        // given
        CustomerDTO customerDTO = new CustomerDTO(3L, "Bruce", "Wayne");
        // when
        Customer customer = customerMapper.dtoToCustomer(customerDTO);
        // then
        assertNotNull(customer);
        assertEquals(customerDTO.getId(), customer.getId());
        assertEquals(customerDTO.getFirstName(), customer.getFirstName());
        assertEquals(customerDTO.getLastName(), customer.getLastName());
    }
}