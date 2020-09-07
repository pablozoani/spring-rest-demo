package com.pablozoani.controller;

import com.pablozoani.api.v1.model.CategoryDTO;
import com.pablozoani.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getAllCategories() throws Exception {
        // given
        CategoryDTO fruits = new CategoryDTO(1L, "Fruits"),
                nuts = new CategoryDTO(2L, "Nuts"),
                fresh = new CategoryDTO(3L, "Fresh");
        List<CategoryDTO> categoryDtoList = asList(fruits, nuts, fresh);
        // when
        when(categoryService.getAllCategories()).thenReturn(categoryDtoList);
        mockMvc.perform(get("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categories", hasSize(3)));
    }

    @Test
    void getCategoryByName() throws Exception {
        // given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Fruits");
        // when
        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);
        mockMvc.perform(get("/api/v1/categories/Fruits"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(categoryDTO.getName())));
    }
}