package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.ProductMapper;
import com.pablozoani.api.v1.mapper.VendorMapper;
import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.api.v1.model.ProductDTOList;
import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.domain.Product;
import com.pablozoani.domain.Vendor;
import com.pablozoani.exception.ResourceNotFoundException;
import com.pablozoani.repository.ProductRepository;
import com.pablozoani.repository.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;

    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    private final VendorRepository vendorRepository;

    @Autowired
    public VendorServiceImpl(VendorMapper vendorMapper, ProductMapper productMapper, ProductRepository productRepository, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(vendorMapper::vendorToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorMapper.vendorToDto(vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor " + id + " not found")));
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.dtoToVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.vendorToDto(savedVendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null) vendor.setName(vendorDTO.getName());
            vendor = vendorRepository.save(vendor);
            return vendorMapper.vendorToDto(vendor);
        }).orElseThrow(() -> new ResourceNotFoundException("Vendor " + id + " not found"));
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        vendorDTO.setId(id);
        Vendor vendor = vendorMapper.dtoToVendor(vendorDTO);
        vendor = vendorRepository.save(vendor);
        return vendorMapper.vendorToDto(vendor);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public ProductDTOList getProductsByVendorId(Long id) {
        return vendorRepository.findById(id).map(vendor -> {
            List<ProductDTO> productDTOS = vendor.getProducts().stream()
                    .map(productMapper::productToDto)
                    .collect(Collectors.toList());
            return ProductDTOList.of(productDTOS);
        }).orElseThrow(() -> new ResourceNotFoundException("Vendor " + id + " not found"));
    }

    @Override
    public ProductDTO addProductToVendor(Long id, ProductDTO productDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            Product product = productMapper.dtoToProduct(productDTO);
            product.setVendor(vendor);
            product = productRepository.save(product);
            return productMapper.productToDto(product);
        }).orElseThrow(() -> new ResourceNotFoundException("Vendor " + id + " not found"));
    }
}
