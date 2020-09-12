package com.pablozoani.controller;
import com.pablozoani.api.v1.model.ProductPhotoDTO;
import com.pablozoani.service.ProductPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(ProductPhotoController.BASE_URL)
public class ProductPhotoController {

    public static final String BASE_URL = "/photo";

    private final ProductPhotoService productPhotoService;

    @Autowired
    public ProductPhotoController(ProductPhotoService productPhotoService) {
        this.productPhotoService = productPhotoService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public ProductPhotoDTO getProductPhotoByProductId(@PathVariable Long id) {
        return productPhotoService.getProductPhotoById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(CREATED)
    public ProductPhotoDTO saveProductPhoto(@RequestBody ProductPhotoDTO productPhotoDTO) {
        return productPhotoService.createProductPhoto(productPhotoDTO);
    }
}
