package com.thefreedevelopers.spring_mvc_tutorial.database.seeder;

import com.thefreedevelopers.spring_mvc_tutorial.database.factory.ProductFactory;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Category;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;
import com.thefreedevelopers.spring_mvc_tutorial.repository.CategoryRepository;
import com.thefreedevelopers.spring_mvc_tutorial.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeder class for generating fake product data based on ProductFactory
 * <p>
 * Created by EdgarEldy on 5/12/2025
 * Author : EdgarEldy
 * Date : 5/12/2025
 * Project : spring_mvc_tutorial
 */

@Component
@Profile("dev")
public class ProductSeeder {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    /**
     * Constructor with dependency injection for required repositories.
     *
     * @param categoryRepository repository for accessing Category data
     * @param productRepository  repository for saving Product data
     */
    public ProductSeeder(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    /**
     * Seeds the Product table with fake data if it's currently empty.
     * It first fetches all categories, then creates fake products and assigns
     * each one a random category before saving them in the database.
     */
    public void seed() {
        // Only seed if there are no existing products
        if (productRepository.count() == 0) {
            // Retrieve all existing categories from the database
            List<Category> categories = categoryRepository.findAll();

            // Generate a list of 10 products with random category assignments
            List<Product> products = ProductFactory.createProducts(10, categories);

            // Save all generated products into the database
            productRepository.saveAll(products);
        }
    }
}
