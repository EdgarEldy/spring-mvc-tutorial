package com.thefreedevelopers.spring_mvc_tutorial.database.factory;

import com.github.javafaker.Faker;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Customer;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Order;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;

/**
 * Factory class for generating fake order data using Java Faker
 * <p>
 * Created by EdgarEldy on 5/12/2025
 * Author : EdgarEldy
 * Date : 5/12/2025
 * Project : spring_mvc_tutorial
 */

public class OrderFactory {
    // Shared Faker instance for generating fake data
    private static final Faker faker = new Faker();

    /**
     * Creates a single Order with a quantity, total, and the given customer and product.
     *
     * @param customer the Customer to assign to the Order
     * @param product  the Product to assign to the Order
     * @return a Order instance with dummy data
     */
    public static Order create(Customer customer, Product product) {
        int quantity = faker.number().numberBetween(1, 100);
        double unitPrice = Double.parseDouble(faker.commerce().price());
        double total = quantity * unitPrice;

        return new Order(
                quantity,
                total,
                customer,
                product
        );
    }
}
