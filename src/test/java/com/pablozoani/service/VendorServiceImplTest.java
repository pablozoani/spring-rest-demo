package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.VendorMapper;
import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.domain.Vendor;
import com.pablozoani.exception.ResourceNotFoundException;
import com.pablozoani.repository.VendorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
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

    @Test
    void createVendor() {
        // given
        Vendor vendor = new Vendor(null, "Nuts for Nuts Company");
        // when
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        VendorDTO vendorDTO = vendorService.createVendor(vendorMapper.vendorToDto(vendor));
        // then
        assertNotNull(vendorDTO);
        assertEquals(vendor.getName(), vendorDTO.getName());
    }

    @Test
    void getVendorById() {
        // given
        Vendor vendor = new Vendor(12L, "Nuts for Nuts Company");
        // when
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        VendorDTO vendorDTO = vendorService.getVendorById(12L);
        // then
        assertEquals(vendor.getId(), vendorDTO.getId());
        assertEquals(vendor.getName(), vendorDTO.getName());
        // and when
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());
        // then
        assertThrows(ResourceNotFoundException.class, () -> vendorService.getVendorById(12L));
    }

    @Test
    void patchVendor() {
        // given
        Vendor vendor = new Vendor(12L, "Nuts for Nuts Company");
        VendorDTO vendorDTO = new VendorDTO(12L, "Nuts Company");
        // when
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        VendorDTO vendorDTO1 = vendorService.patchVendor(12L, vendorDTO);
        // then
        assertEquals(12L, vendorDTO1.getId());
        assertEquals(vendorDTO.getName(), vendorDTO1.getName());
        verify(vendorRepository).findById(anyLong());
        verify(vendorRepository).save(any(Vendor.class));
    }

    @Test
    void updateVendor() {
        // given
        VendorDTO vendorDTO = new VendorDTO(null, "Exotic Fruits 2");
        // when
        when(vendorRepository.save(any(Vendor.class)))
                .thenReturn(vendorMapper.dtoToVendor(new VendorDTO(12L, "Exotic Fruits 2")));
        VendorDTO vendorDTO1 = vendorService.updateVendor(12L, vendorDTO);
        //
        assertEquals(vendorDTO.getId(), vendorDTO1.getId());
        assertEquals(vendorDTO.getName(), vendorDTO1.getName());
        verify(vendorRepository).save(any(Vendor.class));
    }

    @Test
    void deleteVendorById() {
        vendorService.deleteVendorById(12L);
        verify(vendorRepository).deleteById(anyLong());
    }
}