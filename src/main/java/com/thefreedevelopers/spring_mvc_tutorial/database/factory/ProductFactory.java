package com.thefreedevelopers.spring_mvc_tutorial.database.factory;

import com.github.javafaker.Faker;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Category;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;

/**
 * Factory class for generating fake product data for testing
 * <p>
 * Created by EdgarEldy on 5/12/2025
 * Author : EdgarEldy
 * Date : 5/12/2025
 * Project : spring_mvc_tutorial
 */
public class ProductFactory {
    private static final Faker faker = new Faker();

    // Initialize product for testing
    public static Product create(Category category) {
        return new Product(
                faker.commerce().productName(),
                Double.parseDouble(faker.commerce().price()),
                category
        );
    }
}
