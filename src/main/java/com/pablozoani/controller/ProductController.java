package com.pablozoani.controller;

import com.pablozoani.api.v1.model.ProductDTO;
import com.pablozoani.api.v1.model.ProductDTOList;
import com.pablozoani.domain.Product;
import com.pablozoani.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(ProductController.BASE_URL)
public class ProductController {

    public static final String BASE_URL = "/api/v1/products";

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(OK)
    public ProductDTOList getAllProducts() {
        return ProductDTOList.of(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @PutMapping("/${id}")
    @ResponseStatus(OK)
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @PatchMapping("/${id}")
    @ResponseStatus(OK)
    public ProductDTO patchProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.patchProduct(id, productDTO);
    }

    @DeleteMapping("/${id}")
    @ResponseStatus(OK)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
    }


}
