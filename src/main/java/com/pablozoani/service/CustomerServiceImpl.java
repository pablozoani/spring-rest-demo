package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.CustomerMapper;
import com.pablozoani.api.v1.mapper.OrderMapper;
import com.pablozoani.api.v1.model.CustomerDTO;
import com.pablozoani.api.v1.model.OrderDTO;
import com.pablozoani.domain.Customer;
import com.pablozoani.domain.Order;
import com.pablozoani.exception.ResourceNotFoundException;
import com.pablozoani.repository.CustomerRepository;
import com.pablozoani.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.pablozoani.domain.State.CREATED;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Autowired
    CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, OrderRepository orderRepository, OrderMapper orderMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerMapper.customerToDto(customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + id + " not found")));
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.dtoToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToDto(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        Customer customer = customerMapper.dtoToCustomer(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.customerToDto(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId).map(customer -> {
            if (customerDTO.getFirstName() != null) customer.setFirstName(customerDTO.getFirstName());
            if (customerDTO.getLastName() != null) customer.setLastName(customerDTO.getLastName());
            return customerMapper.customerToDto(customerRepository.save(customer));
        }).orElseThrow(() -> new ResourceNotFoundException("Customer " + customerId + " not found"));
    }

    @Override
    public void deleteCustomerById(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Customer " + id + " not found");
        }
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(Long id) {
        return customerRepository.findById(id).map(customer -> customer.getOrders()
                .stream()
                .map(orderMapper::orderToDto)
                .collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + id + " not found."));
    }

    @Override
    public OrderDTO createOrder(Long customerId, OrderDTO orderDTO) {
        log.debug("createOrder({}, {})", customerId, orderDTO);
        Order order = orderMapper.dtoToOrder(orderDTO);
        order.setState(CREATED);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + customerId + " not found"));
        order.setCustomer(customer);
        order = orderRepository.save(order);
        return orderMapper.orderToDto(order);
    }
}
