package com.pablozoani.controller;

import com.pablozoani.api.v1.model.CustomerDTO;
import com.pablozoani.api.v1.model.CustomerListDTO;
import com.pablozoani.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseStatus(OK)
    @GetMapping
    public CustomerListDTO getAllCustomers() {
        return new CustomerListDTO(customerService.getAllCustomers()
                .stream()
                .map(dto -> dto.add(linkTo(methodOn(CustomerController.class)
                        .getCustomerById(dto.getId())).withSelfRel()))
                .collect(Collectors.toList()));
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createCustomer(customerDTO);
    }

    @ResponseStatus(OK)
    @GetMapping(value = "/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .add(linkTo(methodOn(CustomerController.class).getAllCustomers())
                        .withRel("all_customers"));
    }

    @ResponseStatus(OK)
    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id,
                                      @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @ResponseStatus(OK)
    @PatchMapping("/{id}")
    public CustomerDTO patchCustomer(@PathVariable Long id,
                                     @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(id, customerDTO);
    }

    @ResponseStatus(OK)
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }
}
