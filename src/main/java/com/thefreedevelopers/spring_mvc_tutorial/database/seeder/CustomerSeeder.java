package com.thefreedevelopers.spring_mvc_tutorial.database.seeder;

import com.thefreedevelopers.spring_mvc_tutorial.repository.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
}
