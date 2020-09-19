package com.pablozoani.controller;

import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.api.v1.model.VendorDTO;
import com.pablozoani.service.VendorService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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

    @GetMapping(produces = {"application/json", "application/hal+json"})
    @ResponseStatus(OK)
    public CollectionModel<VendorDTO> getAllVendors() {
        return CollectionModel.of(vendorService.getAllVendors()
                .stream()
                .map(vendorDTO -> vendorDTO.add(linkTo(methodOn(VendorController.class)
                        .getVendorById(vendorDTO.getId())).withSelfRel()))
                .collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createVendor(vendorDTO);
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/hal+json"})
    @ResponseStatus(OK)
    public EntityModel<VendorDTO> getVendorById(@PathVariable Long id) {
        return EntityModel.of(vendorService.getVendorById(id)
                .add(linkTo(methodOn(VendorController.class)
                                .getAllVendors())
                                .withRel("all_vendors"),
                        linkTo(methodOn(VendorController.class)
                                .getProductsOfVendor(id))
                                .withRel("vendor_products")));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.updateVendor(id, vendorDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(OK)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(id, vendorDTO);
    }

    @GetMapping(value = "/{id}/products", produces = {"application/json", "application/hal+json"})
    @ResponseStatus(OK)
    public CollectionModel<ProductDTO> getProductsOfVendor(@PathVariable Long id) {
        return CollectionModel.of(vendorService.getProductsByVendorId(id)
                .stream()
                .map(productDTO -> productDTO.add(linkTo(methodOn(ProductController.class)
                        .getProductById(productDTO.getId()))
                        .withSelfRel()))
                .collect(Collectors.toList()));
    }

    @PostMapping("/{id}/products")
    @ResponseStatus(CREATED)
    public ProductDTO addProductToVendor(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return vendorService.addProductToVendor(id, productDTO);
    }
}
