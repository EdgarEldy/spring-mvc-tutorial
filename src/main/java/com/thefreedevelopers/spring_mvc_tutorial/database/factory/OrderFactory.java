package com.thefreedevelopers.spring_mvc_tutorial.database.factory;

import com.github.javafaker.Faker;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Customer;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Order;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        int qty = faker.number().numberBetween(1, 100);
        double unitPrice = Double.parseDouble(faker.commerce().price());
        double total = qty * unitPrice;

        return new Order(
                qty,
                total,
                customer,
                product
        );
    }

    /**
     * Generates a list of orders with random customers and products
     *
     * @param count     the number of orders to create
     * @param customers the list of existing customers to randomly assign to orders
     * @param products  the list of existing products to randomly assign to orders
     * @return a list of Order instances
     */
    public static List<Order> createOrders(int count, List<Customer> customers, List<Product> products) {
        Random random = new Random();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // Pick a random customer and product
            Customer randomCustomer = customers.get(random.nextInt(customers.size()));
            Product randomProduct = products.get(random.nextInt(products.size()));

            // Create a product with the selected customer and product and add to the list
            orders.add(create(randomCustomer, randomProduct));
        }
        return orders;
    }
}
