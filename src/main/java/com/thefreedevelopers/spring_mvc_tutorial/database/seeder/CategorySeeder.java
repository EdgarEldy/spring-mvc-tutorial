package com.thefreedevelopers.spring_mvc_tutorial.database.seeder;

import com.thefreedevelopers.spring_mvc_tutorial.database.factory.CategoryFactory;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Category;
import com.thefreedevelopers.spring_mvc_tutorial.repository.CategoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeder class for generating fake category data based on CategoryFactory
 *
 * Created by EdgarEldy on 5/12/2025
 * Author : EdgarEldy
 * Date : 5/12/2025
 * Project : spring_mvc_tutorial
 */

@Component
@Profile("dev")
public class CategorySeeder {
    private final CategoryRepository categoryRepository;

    public CategorySeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Generate 10 categories for testing
    public void seed() {
        if (categoryRepository.count() == 0) {
            List<Category> categories = CategoryFactory.createCategories(10);
            categoryRepository.saveAll(categories);
        }
    }
}
