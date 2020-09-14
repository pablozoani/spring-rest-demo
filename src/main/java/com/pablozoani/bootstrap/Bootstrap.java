package com.pablozoani.bootstrap;

import com.pablozoani.domain.*;
import com.pablozoani.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
@Profile("default")
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    private final CustomerRepository customerRepository;

    private final VendorRepository vendorRepository;

    private final ProductRepository productRepository;

    private final ProductPhotoRepository productPhotoRepository;

    private final ResourceLoader resourceLoader;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository,
                     VendorRepository vendorRepository, ProductRepository productRepository,
                     ProductPhotoRepository productPhotoRepository, ResourceLoader resourceLoader) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
        this.productPhotoRepository = productPhotoRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String[] args) {
        loadCategories();
        loadCustomers();
        loadVendors();
        loadProducts();
        loadProductPhotos();
    }

    private void loadProductPhotos() {
        ProductPhoto apple = new ProductPhoto(null, "apple.jpg", "image/jpeg",
                getProductPhotoFromResources("apple.jpg"),
                productRepository.findByName("Apple Pack").orElse(null));
        ProductPhoto oranges = new ProductPhoto(null, "oranges.jpg", "image/jpeg",
                getProductPhotoFromResources("oranges.jpg"),
                productRepository.findByName("Extra Acid Oranges").orElse(null));
        ProductPhoto pineapple = new ProductPhoto(null, "pineapple.jpg", "image/jpeg",
                getProductPhotoFromResources("pineapple.jpg"),
                productRepository.findByName("Pineapples").orElse(null));
        List<ProductPhoto> productPhotos = asList(apple, oranges, pineapple);
        productPhotoRepository.saveAll(productPhotos);
        log.debug("Data Loaded. " + productPhotoRepository.count() + " product photos saved into the database.");
    }

    private byte[] getProductPhotoFromResources(String fileName) {
        try {
            return Files.readAllBytes(resourceLoader
                    .getResource("classpath:bootstrap/images/" + fileName)
                    .getFile()
                    .toPath());
        } catch (IOException exc) {
            exc.printStackTrace();
            throw new RuntimeException(exc.getMessage());
        }
    }

    private void loadProducts() {
        Product apples = new Product(null, "Apple Pack", 11.0, null,
                vendorRepository.findById(1L).orElseThrow(RuntimeException::new)),
                oranges = new Product(null, "Extra Acid Oranges", 12.0, null,
                        vendorRepository.findById(2L).orElseThrow(RuntimeException::new)),
                pineapples = new Product(null, "Pineapples", 18.0, null,
                        vendorRepository.findById(3L).orElseThrow(RuntimeException::new));
        List<Product> products = asList(apples, oranges, pineapples);
        productRepository.saveAll(products);
        log.debug("Data Loaded. " + productRepository.count() + " products saved into the database.");
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor(null, "Exotic Fruits Company"),
                vendor2 = new Vendor(null, "Home Fruits"),
                vendor3 = new Vendor(null, "Fun Fresh Fruits Ltd."),
                vendor4 = new Vendor(null, "Nuts for Nuts Company");
        List<Vendor> vendors = asList(vendor1, vendor2, vendor3, vendor4);
        vendorRepository.saveAll(vendors);
        log.debug("Data Loaded. " + vendorRepository.count() + " vendors saved into the database.");
    }

    private void loadCategories() {
        Category fruits = new Category(null, "Fruits"),
                dried = new Category(null, "Dried"),
                fresh = new Category(null, "Fresh"),
                exotic = new Category(null, "Exotic"),
                nuts = new Category(null, "Nuts");
        categoryRepository.saveAll(asList(fruits, dried, fresh, exotic, nuts));
        log.debug("Data Loaded. " + categoryRepository.count() + " categories saved into the database.");
    }

    private void loadCustomers() {
        Customer poe = new Customer(null, "Edgar Allan", "Poe"),
                nietzsche = new Customer(null, "Friedrich", "Nietzsche"),
                rousseau = new Customer(null, "Jean-Jacques", "Rousseau"),
                hugo = new Customer(null, "Victor", "Hugo");
        customerRepository.saveAll(asList(poe, nietzsche, rousseau, hugo));
        log.debug("Data Loaded. " + categoryRepository.count() + " customers saved into the database.");
    }
}
