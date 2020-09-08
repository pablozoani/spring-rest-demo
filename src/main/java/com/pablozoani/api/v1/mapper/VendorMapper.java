package com.pablozoani.api.v1.mapper;

import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToDto(Vendor vendor);
}
