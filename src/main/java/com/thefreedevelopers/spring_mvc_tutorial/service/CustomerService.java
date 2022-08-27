package com.thefreedevelopers.spring_mvc_tutorial.service;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Customer;
import com.thefreedevelopers.spring_mvc_tutorial.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    // Constructor
    public CustomerService() {
    }

    // Initialize CustomerRepository
    @Autowired
    private CustomerRepository customerRepository;

    // Getting all customers
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    // Save a customer
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    //Get a customer using id
    public Customer getCustomerById(long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        Customer customer = null;
        if (customerOptional.isPresent()) {
            customer = customerOptional.get();
        } else {
            throw new RuntimeException("Customer not found:" + id);
        }
        return customer;
    }

    // Remove a customer
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }
}
