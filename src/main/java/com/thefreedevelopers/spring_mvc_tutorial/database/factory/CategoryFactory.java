package com.thefreedevelopers.spring_mvc_tutorial.database.factory;

import com.github.javafaker.Faker;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for generating fake category data using Java Faker
 *
 * Created by EdgarEldy on 5/12/2025
 * Author : EdgarEldy
 * Date : 5/12/2025
 * Project : spring_mvc_tutorial
 */

public class CategoryFactory {
    private static final Faker faker = new Faker();

    // Create fake category names
    public static Category create() {
        return new Category(faker.commerce().department()); // e.g., "Electronics", "Books", "Toys"
    }

    // Create fake categories based on count parameter: e.g, 10 categories
    public static List<Category> createCategories(int count) {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            categories.add(create());
        }

        return categories;
    }
}
