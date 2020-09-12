package com.pablozoani.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.api.v1.model.ProductDTOList;
import com.pablozoani.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void getAllProducts() throws Exception {
        // given
        ProductDTO apple = new ProductDTO(1L, "Apple Pack", 10.0),
                strawberry = new ProductDTO(2L, "Strawberry Box", 8.0);
        List<ProductDTO> products = asList(apple, strawberry);
        // when
        when(productService.getAllProducts()).thenReturn(products);
        ResultActions resultActions = mockMvc.perform(get(ProductController.BASE_URL)
                .accept(APPLICATION_JSON));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(2)))
                .andReturn().getResponse();
        ProductDTOList productDTOList = objectMapper.readValue(response.getContentAsString(), ProductDTOList.class);
        productDTOList.getProducts().forEach(Assertions::assertNotNull);
    }

    @Test
    void createProduct() {
        // TODO
    }

    @Test
    void updateProduct() {
        // TODO
    }

    @Test
    void patchProduct() {
        // TODO
    }

    @Test
    void deleteProduct() {
        // TODO
    }

    @Test
    void getProductById() {
        // TODO
    }
}