package com.thefreedevelopers.spring_mvc_tutorial.database.factory;

import com.github.javafaker.Faker;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Category;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory class for generating fake product data using Java Faker
 *
 * Created by EdgarEldy on 5/12/2025
 * Author : EdgarEldy
 * Date : 5/12/2025
 * Project : spring_mvc_tutorial
 */
public class ProductFactory {
    // Shared Faker instance for generating fake data
    private static final Faker faker = new Faker();

    /**
     * Creates a single Product with a random product name, price, and the given category.
     *
     * @param category the Category to assign to the Product
     * @return a Product instance with dummy data
     */
    public static Product create(Category category) {
        return new Product(
                faker.commerce().productName(),
                Double.parseDouble(faker.commerce().price()),
                category
        );
    }

    /**
     * Generates a list of Products with random product data and random categories.
     *
     * @param count      the number of products to create
     * @param categories the list of existing categories to randomly assign to products
     * @return a list of Product instances
     */
    public static List<Product> createProducts(int count, List<Category> categories) {
        Random random = new Random();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // Pick a random Category from the list
            Category randomCategory = categories.get(random.nextInt(categories.size()));

            // Create a product with the selected category and add to the list
            products.add(create(randomCategory));
        }
        return products;
    }
}
