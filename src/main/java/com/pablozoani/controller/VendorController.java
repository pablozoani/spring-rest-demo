package com.pablozoani.controller;

import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.api.v1.model.VendorDTOList;
import com.pablozoani.service.VendorService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(OK)
    public VendorDTOList getAllVendors() {
        return new VendorDTOList(vendorService.getAllVendors());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createVendor(vendorDTO);
    }
}
