package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.VendorMapper;
import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.domain.Vendor;
import com.pablozoani.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;

    private final VendorRepository vendorRepository;

    @Autowired
    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(vendor -> vendorMapper.vendorToDto(vendor))
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.dtoToVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.vendorToDto(savedVendor);
    }
}
