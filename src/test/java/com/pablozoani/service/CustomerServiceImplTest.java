package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.CustomerMapper;
import com.pablozoani.api.v1.mapper.OrderMapper;
import com.pablozoani.api.v1.model.CustomerDTO;
import com.pablozoani.domain.Customer;
import com.pablozoani.exception.ResourceNotFoundException;
import com.pablozoani.repository.CustomerRepository;
import com.pablozoani.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    OrderRepository orderRepository;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    OrderMapper orderMapper = OrderMapper.INSTANCE;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, customerMapper, orderRepository, orderMapper);
    }

    @Test
    void getAllCustomers() {
        // given
        Customer pablo = new Customer(1L, "Pablo", "Zoani"),
                john = new Customer(2L, "John", "Doe"),
                clark = new Customer(3L, "Clark", "Kent");
        List<Customer> customers = asList(pablo, john, clark);
        // when
        when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDTO> customerDTOList = customerService.getAllCustomers();
        // then
        assertEquals(customers.size(), customerDTOList.size());
        customerDTOList.forEach(Assertions::assertNotNull);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById() {
        // given
        Customer poe = new Customer(1L, "Edgar Allan", "Poe");
        // when
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(poe));
        CustomerDTO customerDTO = customerService.getCustomerById(1L);
        // then
        assertNotNull(customerDTO);
        assertEquals(poe.getId(), customerDTO.getId());
        assertEquals(poe.getFirstName(), customerDTO.getFirstName());
        assertEquals(poe.getLastName(), customerDTO.getLastName());
        verify(customerRepository, times(1)).findById(1L);
        // and when
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        // then
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(12L));
    }

    @Test
    void createCustomer() {
        // given
        CustomerDTO customerDTO = new CustomerDTO(11L, "Ludwig", "van Beethoven");
        // when
        when(customerRepository.save(any())).thenReturn(customerMapper.dtoToCustomer(customerDTO));
        CustomerDTO newCustomer = customerService.createCustomer(customerDTO);
        // then
        assertNotNull(newCustomer);
        assertEquals(customerDTO.getId(), newCustomer.getId());
        assertEquals(customerDTO.getFirstName(), newCustomer.getFirstName());
        assertEquals(customerDTO.getLastName(), newCustomer.getLastName());
    }

    @Test
    void updateCustomer() {
        // given
        CustomerDTO customerDTO = new CustomerDTO(19L, "John", "Doe");
        // when
        when(customerRepository.save(any(Customer.class))).thenReturn(customerMapper.dtoToCustomer(customerDTO));
        CustomerDTO updatedCustomer = customerService.updateCustomer(19L, customerDTO);
        // then
        assertNotNull(updatedCustomer);
        assertEquals(19L, updatedCustomer.getId());
        assertEquals(customerDTO.getFirstName(), updatedCustomer.getFirstName());
        assertEquals(customerDTO.getLastName(), updatedCustomer.getLastName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void patchCustomer() {
        // given
        CustomerDTO customerDTO = new CustomerDTO(23L, "Isaac", null);
        Customer customer = new Customer(23L, "Isac", "Newton");
        // when
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(customer);
        CustomerDTO patchedCustomer = customerService.patchCustomer(23L, customerDTO);
        // then
        assertNotNull(patchedCustomer);
        assertEquals("Isaac", patchedCustomer.getFirstName());
        assertEquals("Newton", patchedCustomer.getLastName());
        verify(customerRepository, times(1)).findById(any(Long.class));
        // and when
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        // then
        assertThrows(ResourceNotFoundException.class, () -> customerService.patchCustomer(127L, customerDTO));
    }

    @Test
    void deleteCustomerById() {
        customerService.deleteCustomerById(19L);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void getOrdersByCustomerId() {
        // TODO
    }

    @Test
    void createOrder() {
        // TODO
    }
}