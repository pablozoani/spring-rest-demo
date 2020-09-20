package com.pablozoani.bootstrap;

import com.pablozoani.domain.*;
import com.pablozoani.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final ResourceLoader resourceLoader;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository,
                     VendorRepository vendorRepository, ProductRepository productRepository,
                     ProductPhotoRepository productPhotoRepository, OrderRepository orderRepository,
                     ItemRepository itemRepository, ResourceLoader resourceLoader) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
        this.productPhotoRepository = productPhotoRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String[] args) {
        loadCategories();
        loadCustomers();
        loadVendors();
        loadProducts();
        loadProductPhotos();
        loadOrders();
        loadItems();
    }

    private void loadItems() {
        Order order1 = orderRepository.findById(1L).orElseThrow(RuntimeException::new);
        Item item1 = new Item(null, 5, order1, productRepository.findById(1L)
                .orElseThrow(RuntimeException::new));
        Item item2 = new Item(null, 4, order1, productRepository.findById(2L)
                .orElseThrow(RuntimeException::new));
        itemRepository.save(item1);
        itemRepository.save(item2);
        Order order2 = orderRepository.findById(2L).orElseThrow(RuntimeException::new);
        Item item3 = new Item(null, 3, order2, productRepository.findById(3L)
                .orElseThrow(RuntimeException::new));
        Item item4 = new Item(null, 6, order2, productRepository.findById(4L)
                .orElseThrow(RuntimeException::new));
        itemRepository.save(item3);
        itemRepository.save(item4);
    }

    @Transactional
    private void loadOrders() {
        Order order1 = new Order(State.CREATED);
        Customer customer1 = customerRepository.findById(1L)
                .orElseThrow(RuntimeException::new);
        order1.setCustomer(customer1);
        orderRepository.save(order1);
        Order order2 = new Order(State.CREATED);
        Customer customer2 = customerRepository.findById(2L)
                .orElseThrow(RuntimeException::new);
        order2.setCustomer(customer2);
        orderRepository.save(order2);
        log.debug("Data Loaded. " + orderRepository.count() + " orders saved into the database.");
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
        ProductPhoto driedMix = new ProductPhoto(null, "dried_mix.jpg", "image/jpeg",
                getProductPhotoFromResources("dried_mix.jpg"),
                productRepository.findByName("Dried Mix").orElse(null));
        ProductPhoto almonds = new ProductPhoto(null, "almonds.jpg", "image/jpeg",
                getProductPhotoFromResources("almonds.jpg"),
                productRepository.findByName("Almonds").orElse(null));
        List<ProductPhoto> productPhotos = asList(apple, oranges, pineapple,
                driedMix, almonds);
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
                vendorRepository.findById(1L).orElseThrow(RuntimeException::new),
                categoryRepository.findByName("Fruits").orElseThrow(RuntimeException::new)),
                oranges = new Product(null, "Extra Acid Oranges", 12.0, null,
                        vendorRepository.findById(2L).orElseThrow(RuntimeException::new),
                        categoryRepository.findByName("Fruits")
                                .orElseThrow(RuntimeException::new)),
                pineapples = new Product(null, "Pineapples", 18.0, null,
                        vendorRepository.findById(3L).orElseThrow(RuntimeException::new),
                        categoryRepository.findByName("Fruits")
                                .orElseThrow(RuntimeException::new)),
                driedMix = new Product(null, "Dried Mix", 15.0, null,
                        vendorRepository.findById(1L).orElseThrow(RuntimeException::new),
                        categoryRepository.findByName("Dried")
                                .orElseThrow(RuntimeException::new)),
                almonds = new Product(null, "Almonds", 9.0, null,
                        vendorRepository.findById(4L).orElseThrow(RuntimeException::new),
                        categoryRepository.findByName("Nuts")
                                .orElseThrow(RuntimeException::new));
        List<Product> products = asList(apples, oranges, pineapples,
                driedMix, almonds);
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
