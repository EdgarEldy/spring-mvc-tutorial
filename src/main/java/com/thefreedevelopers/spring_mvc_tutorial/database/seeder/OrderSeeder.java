package com.thefreedevelopers.spring_mvc_tutorial.database.seeder;

import com.thefreedevelopers.spring_mvc_tutorial.database.factory.OrderFactory;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Customer;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Order;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;
import com.thefreedevelopers.spring_mvc_tutorial.repository.CustomerRepository;
import com.thefreedevelopers.spring_mvc_tutorial.repository.OrderRepository;
import com.thefreedevelopers.spring_mvc_tutorial.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeder class for generating fake order data based on OrderFactory
 * <p>
 * Created by EdgarEldy on 5/12/2025
 * Author : EdgarEldy
 * Date : 5/12/2025
 * Project : spring_mvc_tutorial
 */

@Component
@Profile("dev")
public class OrderSeeder {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    /**
     * Constructor with dependency injection for required repositories.
     *
     * @param customerRepository repository for accessing Customer data
     * @param productRepository  repository for accessing Product data
     * @param orderRepository    repository for saving Order data
     */
    public OrderSeeder(CustomerRepository customerRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Seeds the Order table with fake data if it's currently empty.
     * It first fetches all customers and products, then creates fake orders and assigns
     * each one a random customer and product before saving them in the database.
     */
    public void seed() {
        // Only seed if there are no existing orders
        if (orderRepository.count() == 0) {
            // Retrieve all existing customers from the database
            List<Customer> customers = customerRepository.findAll();

            // Retrieve all existing products from the database
            List<Product> products = productRepository.findAll();

            // Generate a list of 10 orders with random customer and product assignments
            List<Order> orders = OrderFactory.createOrders(10, customers, products);

            // Save all generated orders into the database
            orderRepository.saveAll(orders);
        }
    }
}
