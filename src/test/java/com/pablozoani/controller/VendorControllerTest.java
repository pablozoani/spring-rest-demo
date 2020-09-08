package com.pablozoani.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pablozoani.api.v1.mapper.VendorMapper;
import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.api.v1.model.VendorDTOList;
import com.pablozoani.domain.Vendor;
import com.pablozoani.service.VendorService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VendorControllerTest {

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    ObjectMapper objectMapper = new ObjectMapper();

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController).build();
    }

    @Test
    void getAllVendors() throws Exception {
        // given
        VendorDTO vendor1 = new VendorDTO(null, "Western Tasty Fruits Ltd."),
                vendor2 = new VendorDTO(null, "Exotic Fruits Company"),
                vendor3 = new VendorDTO(null, "Home Fruits");
        List<VendorDTO> vendors = asList(vendor1, vendor2, vendor3);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println(objectMapper.writeValueAsString(vendors));
        // when
        when(vendorService.getAllVendors()).thenReturn(vendors);
        ResultActions resultActions = mockMvc.perform(get(VendorController.BASE_URL).accept(APPLICATION_JSON));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.vendors", hasSize(vendors.size())))
                .andReturn().getResponse();
        objectMapper.readValue(response.getContentAsString(), VendorDTOList.class)
                .getVendors().forEach(Assertions::assertNotNull);
    }

    @Test
    void createVendor() throws Exception {
        // given
        VendorDTO vendor = new VendorDTO(null, "Western Tasty Fruits Ltd.");
        // when
        when(vendorService.createVendor(any(VendorDTO.class))).thenReturn(vendor);
        ResultActions resultActions = mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendor)));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(vendor)))
                .andReturn().getResponse();
        VendorDTO vendor2 = objectMapper.readValue(response.getContentAsString(), VendorDTO.class);
        assertEquals(vendor, vendor2);
    }
}