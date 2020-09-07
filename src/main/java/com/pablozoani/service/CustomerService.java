package com.pablozoani.service;

import com.pablozoani.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO);

    CustomerDTO patchCustomer(Long customerId, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);
}
