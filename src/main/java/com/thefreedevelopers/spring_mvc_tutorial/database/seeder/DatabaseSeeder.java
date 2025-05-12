package com.thefreedevelopers.spring_mvc_tutorial.database.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Main seeder class that triggers individual seeders during application startup
 * This class is executed only when the Spring Boot application starts (if active profile is 'dev')
 * <p>
 * Created by EdgarEldy on 5/12/2025
 * Author : EdgarEldy
 * Date : 5/12/2025
 * Project : spring_mvc_tutorial
 */

@Component
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {
    private final CategorySeeder categorySeeder;
    private final ProductSeeder productSeeder;
    private final CustomerSeeder customerSeeder;
    private final OrderSeeder orderSeeder;

    public DatabaseSeeder(CategorySeeder categorySeeder, ProductSeeder productSeeder, CustomerSeeder customerSeeder, OrderSeeder orderSeeder) {
        this.categorySeeder = categorySeeder;
        this.productSeeder = productSeeder;
        this.customerSeeder = customerSeeder;
        this.orderSeeder = orderSeeder;
    }

    @Override
    public void run(String... args) throws Exception {
        categorySeeder.seed();

        // Seed products with random categories
        productSeeder.seed();

        // Seed customers
        customerSeeder.seed();

        // Seed orders
        orderSeeder.seed();
    }
}
