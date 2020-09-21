package com.pablozoani.controller;

import com.pablozoani.api.v1.model.CategoryDTO;
import com.pablozoani.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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

    @GetMapping(produces = {"application/json", "application/hal+json"})
    @ResponseStatus(OK)
    public CollectionModel<CategoryDTO> getAllCategories() {
        return CollectionModel.of(categoryService.getAllCategories()
                .stream()
                .map(categoryDTO -> categoryDTO.add(linkTo(methodOn(CategoryController.class)
                        .getCategoryByName(categoryDTO.getName()))
                        .withSelfRel()))
                .collect(Collectors.toList()))
                .add(linkTo(methodOn(CategoryController.class)
                        .getAllCategories())
                        .withSelfRel());
    }

    @ResponseStatus(OK)
    @GetMapping(value = "/{name}", produces = {"application/json", "application/hal+json"})
    public EntityModel<CategoryDTO> getCategoryByName(@PathVariable String name) {
        CategoryDTO categoryDTO = categoryService.getCategoryByName(name);
        categoryDTO.getProducts().forEach(productDTO ->
                productDTO.add(linkTo(methodOn(ProductController.class)
                        .getProductById(productDTO.getId())).withSelfRel()));
        return EntityModel.of(categoryDTO.add(linkTo(methodOn(CategoryController.class)
                .getCategoryByName(name))
                .withSelfRel()));
    }

    @ResponseStatus(OK)
    @PutMapping(value = "/{categoryId}/products/{productId}",
            produces = {"application/json"})
    public EntityModel<CategoryDTO> addProductToCategory(@PathVariable Long categoryId,
                                                         @PathVariable Long productId) {
        return EntityModel.of(categoryService.addProductToCategoryByProductIdAndCategoryId(categoryId, productId));
    }
}
