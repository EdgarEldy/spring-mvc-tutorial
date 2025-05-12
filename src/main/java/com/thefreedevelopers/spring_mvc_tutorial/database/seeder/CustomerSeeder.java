package com.thefreedevelopers.spring_mvc_tutorial.database.seeder;

import com.thefreedevelopers.spring_mvc_tutorial.database.factory.CustomerFactory;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Customer;
import com.thefreedevelopers.spring_mvc_tutorial.repository.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeder class for generating fake customer data based on CustomerFactory
 * <p>
 * Created by EdgarEldy on 5/12/2025
 * Author : EdgarEldy
 * Date : 5/12/2025
 * Project : spring_mvc_tutorial
 */

@Component
@Profile("dev")
public class CustomerSeeder {
    private final CustomerRepository customerRepository;

    /**
     * Constructor with dependency injection for the customerRepository
     *
     * @param customerRepository repository for accessing Customer data
     */
    public CustomerSeeder(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Seed customers
    public void seed() {
        // Only seed if there is no existing customers
        if (customerRepository.count() > 0) {
            // Generate a list of 10 customers
            List<Customer> customers = CustomerFactory.createCustomers(10);

            // Save all generated customers into the database
            customerRepository.saveAll(customers);
        }
    }
}
