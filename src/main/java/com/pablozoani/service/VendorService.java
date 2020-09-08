package com.pablozoani.service;

import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.domain.Vendor;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO createVendor(VendorDTO vendor);
}
