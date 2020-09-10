package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VendorMapperTest {

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    void vendorToDto() {
        // given
        Vendor vendor = new Vendor(6L, "Home Fruits");
        // then
        VendorDTO vendorDto = vendorMapper.vendorToDto(vendor);
        // then
        assertEquals(vendor.getId(), vendorDto.getId());
        assertEquals(vendor.getName(), vendorDto.getName());
    }

    @Test
    void dtoToVendor() {
        // given
        VendorDTO vendor = new VendorDTO(null, "Western Tasty Fruits Ltd.");
        // when
        Vendor vendor1 = vendorMapper.dtoToVendor(vendor);
        // then
        assertNotNull(vendor1);
        assertEquals(vendor.getId(), vendor1.getId());
        assertEquals(vendor.getName(), vendor1.getName());
    }
}