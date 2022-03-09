package com.thefreedevelopers.spring_mvc_tutorial.controller;

import com.thefreedevelopers.spring_mvc_tutorial.service.CustomerService;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {

    // Initialize CustomerService
    private CustomerService customerService;

    // Constructor
    public CustomerController() {
    }
}
