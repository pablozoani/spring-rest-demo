package com.pablozoani.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pablozoani.api.v1.mapper.VendorMapper;
import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        // when
        when(vendorService.getAllVendors()).thenReturn(vendors);
        ResultActions resultActions = mockMvc.perform(get(VendorController.BASE_URL).accept(APPLICATION_JSON));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(CollectionModel.of(vendors))))
                .andReturn().getResponse();
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

    @Test
    void deleteVendorById() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/5"))
                .andExpect(status().isOk());
        verify(vendorService).deleteVendorById(anyLong());
    }

    @Test
    void updateVendor() throws Exception {
        // given
        VendorDTO vendorDTO = new VendorDTO(null, "Western Tasty Fruits Ltd.");
        // when
        when(vendorService.updateVendor(anyLong(), any(VendorDTO.class))).thenReturn(vendorDTO);
        ResultActions resultActions = mockMvc.perform(put(VendorController.BASE_URL + "/4")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendorDTO)));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(vendorDTO)))
                .andReturn().getResponse();
        VendorDTO vendorOutput = objectMapper.readValue(response.getContentAsString(), VendorDTO.class);
        assertEquals(vendorDTO, vendorOutput);
    }

    @Test
    void patchVendor() throws Exception {
        // given
        VendorDTO vendorDTO = new VendorDTO(null, "Exotic Fruits Inc.");
        // when
        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(vendorDTO);
        ResultActions resultActions = mockMvc.perform(patch(VendorController.BASE_URL + "/3")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendorDTO)));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(vendorDTO)))
                .andReturn().getResponse();
        VendorDTO vendorDTO1 = objectMapper.readValue(response.getContentAsString(), VendorDTO.class);
        assertEquals(vendorDTO, vendorDTO1);
    }

    @Test
    void getVendorById() throws Exception {
        // given
        VendorDTO vendorDTO = new VendorDTO(7L, "Awesome Apples Corp");
        // when
        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);
        ResultActions resultActions = mockMvc.perform(get(VendorController.BASE_URL + "/7")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendorDTO)));
        // then
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(EntityModel.of(vendorDTO))))
                .andReturn().getResponse();
        VendorDTO vendorOutput = objectMapper.readValue(response.getContentAsString(), VendorDTO.class);
        assertEquals(vendorDTO, vendorOutput);
    }

    @Test
    void deleteVendor() {
    }

    @Test
    void getProductsOfVendor() throws Exception {
        // given
        List<ProductDTO> productDTOList = asList(new ProductDTO(1L, "Apple Pack", 10.0));
        given(vendorService.getProductsByVendorId(anyLong())).willReturn(productDTOList);
        // when
        ResultActions resultActions = mockMvc.perform(get(VendorController.BASE_URL + "/2/products")
                .accept("application/hal+json"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(content().json(objectMapper.writeValueAsString(CollectionModel.of(productDTOList))));
    }

    @Test
    void addProductToVendor() {
        // TODO
    }
}