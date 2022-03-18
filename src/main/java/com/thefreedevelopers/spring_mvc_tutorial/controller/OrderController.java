package com.thefreedevelopers.spring_mvc_tutorial.controller;

import com.thefreedevelopers.spring_mvc_tutorial.service.CustomerService;
import com.thefreedevelopers.spring_mvc_tutorial.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    // Initialize OrderService
    @Autowired
    private OrderService orderService;

    // Initialize CustomerService
    @Autowired
    private CustomerService customerService;
}
