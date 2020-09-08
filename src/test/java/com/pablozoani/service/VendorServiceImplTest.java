package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.VendorMapper;
import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.domain.Vendor;
import com.pablozoani.repository.VendorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        vendorService = new VendorServiceImpl(vendorMapper, vendorRepository);
    }

    @Test
    void getAllVendors() {
        // given
        Vendor vendor1 = new Vendor(null, "Western Tasty Fruits Ltd."),
                vendor2 = new Vendor(null, "Exotic Fruits Company"),
                vendor3 = new Vendor(null, "Home Fruits");
        List<Vendor> vendors = asList(vendor1, vendor2, vendor3);
        // when
        when(vendorRepository.findAll()).thenReturn(vendors);
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();
        // then
        assertEquals(3, vendorDTOS.size());
        vendorDTOS.forEach(Assertions::assertNotNull);
    }
}