package com.pablozoani.controller;

import com.pablozoani.api.v1.model.CustomerDTO;
import com.pablozoani.api.v1.model.OrderDTO;
import com.pablozoani.service.CustomerService;
import com.pablozoani.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

    private final OrderService orderService;

    @Autowired
    public CustomerController(CustomerService customerService,
                              OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @ResponseStatus(OK)
    @GetMapping(produces = {"application/json", "application/hal+json"})
    public CollectionModel<CustomerDTO> getAllCustomers() {
        return CollectionModel.of(customerService.getAllCustomers()
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
    @GetMapping(value = "/{id}", produces = {"application/json", "application/hal+json"})
    public EntityModel<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return EntityModel.of(customerService.getCustomerById(id)
                .add(linkTo(methodOn(CustomerController.class).getAllCustomers())
                                .withRel("all_customers"),
                        linkTo(methodOn(CustomerController.class)
                                .getCustomerById(id))
                                .withSelfRel()));
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

    @ResponseStatus(OK)
    @GetMapping(value = "/{id}/orders",
            produces = {"application/json", "application/hal+json"})
    public CollectionModel<OrderDTO> getOrders(@PathVariable Long id) {
        return CollectionModel.of(customerService.getOrdersByCustomerId(id)
                .stream()
                .map(orderDTO -> orderDTO.add(linkTo(methodOn(OrderController.class)
                        .getOrderById(orderDTO.getId()))
                        .withSelfRel()))
                .collect(Collectors.toList()));
    }

    @ResponseStatus(CREATED)
    @PostMapping(value = "/{customerId}/orders",
            consumes = {"application/json", "application/hal+json"})
    public EntityModel<OrderDTO> createOrder(@PathVariable Long customerId,
                                             @RequestBody OrderDTO orderDTO) {
        return EntityModel.of(customerService.createOrder(customerId, orderDTO));
    }
}
