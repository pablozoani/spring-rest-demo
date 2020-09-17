package com.pablozoani.controller;

import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.api.v1.model.ProductDTOList;
import com.pablozoani.api.v1.model.ProductPhotoDTO;
import com.pablozoani.service.ProductPhotoService;
import com.pablozoani.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(ProductController.BASE_URL)
public class ProductController {

    public static final String BASE_URL = "/api/v1/products";

    private final ProductService productService;

    private final ProductPhotoService productPhotoService;

    @Autowired
    public ProductController(ProductService productService,
                             ProductPhotoService productPhotoService) {
        this.productService = productService;
        this.productPhotoService = productPhotoService;
    }

    @GetMapping
    @ResponseStatus(OK)
    public ProductDTOList getAllProducts() {
        return ProductDTOList.of(productService.getAllProducts()
                .stream()
                .map(productDTO -> productDTO.add(linkTo(methodOn(ProductController.class)
                        .getProductById(productDTO.getId()))
                        .withSelfRel()))
                .collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .add(linkTo(methodOn(ProductController.class)
                        .getAllProducts())
                        .withRel("all_products"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(OK)
    public ProductDTO patchProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.patchProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @GetMapping(value = "/{id}/photo", produces = "image/jpeg")
    @ResponseStatus(OK)
    public byte[] getProductPhotoByProductId(@PathVariable Long id) {
        return productPhotoService.getProductPhotoByProductId(id).getPhoto();
    }

    @PutMapping(value = "/{id}/photo")
    @ResponseStatus(CREATED)
    public ProductPhotoDTO saveProductPhoto(@PathVariable Long id,
                                            @RequestParam("file") MultipartFile file) {
        return productPhotoService.updateProductPhoto(id, file);
    }
}
