package com.pablozoani.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablozoani.api.v1.model.CustomerDTO;
import com.pablozoani.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.hateoas.CollectionModel;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        // given
        CustomerDTO pablo = new CustomerDTO(1L, "Pablo", "Zoani"),
                john = new CustomerDTO(2L, "John", "Doe"),
                clark = new CustomerDTO(3L, "Clark", "Kent");
        List<CustomerDTO> customers = asList(pablo, john, clark);
        // when
        when(customerService.getAllCustomers()).thenReturn(customers);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/customers")
                .accept("application/hal+json"));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(content().json(objectMapper.writeValueAsString(CollectionModel.of(customers))))
                .andReturn().getResponse();
        response.getHeaderNames()
                .forEach(header -> System.out.println(header + ": " + response.getHeader(header)));
        System.out.println(response.getContentAsString());
    }

    @Test
    void getCustomerById() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO(7L, "Pablo", "Zoani");
        // when
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/customers/7"));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("id", equalTo(7)))
                .andReturn().getResponse();
        response.getHeaderNames()
                .forEach(header -> System.out.println(header + ": " + response.getHeader(header)));
        System.out.println(response.getContentAsString());
    }

    @Test
    void createCustomer() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO(13L, "Albert", "Einstein");
        // when
        when(customerService.createCustomer(any())).thenReturn(customerDTO);
        ResultActions resultActions = mockMvc.perform(post("/api/v1/customers")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO)));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(customerDTO)))
                .andReturn().getResponse();
        response.getHeaderNames()
                .forEach(header -> System.out.println(header + ": " + response.getHeader(header)));
        System.out.println(response.getContentAsString());
    }

    @Test
    void updateCustomer() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO(13L, "Albert", "Einstein");
        // when
        when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(customerDTO);
        ResultActions resultActions = mockMvc.perform(put("/api/v1/customers/13")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO)));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(customerDTO)))
                .andReturn().getResponse();
        response.getHeaderNames()
                .forEach(header -> System.out.println(header + ": " + response.getHeader(header)));
        System.out.println(response.getContentAsString());
    }

    @Test
    void patchCustomer() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO(17L, "Lois", "Lane");
        // when
        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(customerDTO);
        ResultActions resultActions = mockMvc.perform(patch("/api/v1/customers/17")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO)));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(customerDTO)))
                .andReturn().getResponse();
        response.getHeaderNames()
                .forEach(header -> System.out.println(header + ": " + response.getHeader(header)));
        System.out.println(response.getContentAsString());
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/23"))
                .andExpect(status().isOk());
        verify(customerService, times(1)).deleteCustomerById(anyLong());
    }

    @Test
    void getOrders() {
        // TODO
    }

    @Test
    void createOrder() {
        // TODO
    }
}