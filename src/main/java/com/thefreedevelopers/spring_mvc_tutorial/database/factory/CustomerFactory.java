package com.thefreedevelopers.spring_mvc_tutorial.database.factory;

import com.github.javafaker.Faker;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Customer;

/**
 * Factory class for generating fake customer data using Java Faker
 * <p>
 * Created by EdgarEldy on 5/12/2025
 * Author : EdgarEldy
 * Date : 5/12/2025
 * Project : spring_mvc_tutorial
 */

public class CustomerFactory {
    // Shared Faker instance for generating fake data
    private static final Faker faker = new Faker();

    /**
     * Creates a single customer with a random first name, last name, phone number, email and address
     *
     * @return a customer instance with dummy data
     */
    public static Customer create() {
        return new Customer(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.internet().emailAddress(),
                faker.address().fullAddress()
        );
    }
}
