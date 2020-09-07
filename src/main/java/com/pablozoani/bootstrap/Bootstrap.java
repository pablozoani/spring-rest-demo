package com.pablozoani.bootstrap;

import com.pablozoani.domain.Category;
import com.pablozoani.domain.Customer;
import com.pablozoani.repository.CategoryRepository;
import com.pablozoani.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

@Slf4j
@Profile("default")
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String[] args) {
        loadCategories();
        loadCustomers();
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
