package com.pablozoani.service;

import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO getVendorById(Long id);

    VendorDTO createVendor(VendorDTO vendor);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);

    void deleteVendorById(Long id);

    List<ProductDTO> getProductsByVendorId(Long id);

    ProductDTO addProductToVendor(Long id, ProductDTO productDTO);
}
