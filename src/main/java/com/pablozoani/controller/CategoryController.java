package com.pablozoani.controller;

import com.pablozoani.api.v1.model.CategoryDTO;
import com.pablozoani.api.v1.model.CategoryListDTO;
import com.pablozoani.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @ResponseStatus(OK)
    @GetMapping("/{name}")
    public CategoryDTO getCategoryByName(@PathVariable String name) {
        CategoryDTO categoryDTO = categoryService.getCategoryByName(name);
        categoryDTO.getProducts().forEach(productDTO ->
                productDTO.add(linkTo(methodOn(ProductController.class)
                        .getProductById(productDTO.getId())).withSelfRel()));
        return categoryDTO;
    }
}
