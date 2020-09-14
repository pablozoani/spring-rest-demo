package com.pablozoani.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.api.v1.model.ProductDTOList;
import com.pablozoani.api.v1.model.ProductPhotoDTO;
import com.pablozoani.service.ProductPhotoService;
import com.pablozoani.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    ProductService productService;

    @Mock
    ProductPhotoService productPhotoService;

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
        verify(productService).getAllProducts();
    }

    @Test
    void createProduct() throws Exception {
        // given
        ProductDTO productDTO = new ProductDTO(null, "Apple Pack", 10.5);
        given(productService.createProduct(any(ProductDTO.class))).willReturn(productDTO);
        // when
        ResultActions resultActions = mockMvc.perform(post(ProductController.BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO))
                .accept(APPLICATION_JSON));
        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(productDTO)));
        verify(productService).createProduct(any());
    }

    @Test
    void updateProduct() throws Exception {
        // given
        ProductDTO productDTO = new ProductDTO(5L, "Apple Pack", 5.7);
        given(productService.updateProduct(anyLong(), any())).willReturn(productDTO);
        // when
        ResultActions resultActions = mockMvc.perform(put(ProductController.BASE_URL + "/5")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO))
                .accept(APPLICATION_JSON));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(productDTO)));
        verify(productService).updateProduct(anyLong(), any());
    }

    @Test
    void patchProduct() throws Exception {
        // given
        ProductDTO productDTO = new ProductDTO(7L, "Apple Pack", 5.7);
        given(productService.patchProduct(anyLong(), any())).willReturn(productDTO);
        // when
        ResultActions resultActions = mockMvc.perform(patch(ProductController.BASE_URL + "/7")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO))
                .accept(APPLICATION_JSON));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(productDTO)));
        verify(productService).patchProduct(anyLong(), any());
    }

    @Test
    void deleteProduct() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete(ProductController.BASE_URL + "/13"));
        resultActions.andExpect(status().isOk());
        verify(productService).deleteProductById(anyLong());
    }

    @Test
    void getProductById() throws Exception {
        // given
        ProductDTO product = new ProductDTO(17L, "Apple Pack", 11.5);
        given(productService.getProductById(anyLong())).willReturn(product);
        // when
        ResultActions resultActions = mockMvc.perform(get(ProductController.BASE_URL + "/17")
                .accept(APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
        verify(productService).getProductById(anyLong());
    }

    @Test
    void getProductPhotoByProductId() throws Exception {
        // given
        ProductPhotoDTO productPhotoDTO = new ProductPhotoDTO(
                5L, "apple.jpeg", "image/jpeg", new byte[]{'a', 'b'});
        given(productPhotoService.getProductPhotoByProductId(anyLong())).willReturn(productPhotoDTO);
        // when
        ResultActions resultActions = mockMvc.perform(get(ProductController.BASE_URL + "/5/photo")
                .accept(IMAGE_JPEG_VALUE));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(IMAGE_JPEG_VALUE))
                .andReturn().getResponse();
        assertArrayEquals(productPhotoDTO.getPhoto(), response.getContentAsByteArray());
    }
}