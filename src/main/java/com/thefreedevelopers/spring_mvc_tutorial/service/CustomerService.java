package com.thefreedevelopers.spring_mvc_tutorial.service;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Customer;
import com.thefreedevelopers.spring_mvc_tutorial.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    // Constructor
    public CustomerService() {
    }

    // Initialize CustomerRepository
    @Autowired
    private CustomerRepository customerRepository;

    // Getting all customers
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }
}
