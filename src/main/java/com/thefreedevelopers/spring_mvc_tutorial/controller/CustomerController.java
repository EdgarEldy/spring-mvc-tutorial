package com.thefreedevelopers.spring_mvc_tutorial.controller;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Category;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Customer;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;
import com.thefreedevelopers.spring_mvc_tutorial.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerController {

    // Initialize CustomerService
    @Autowired
    private CustomerService customerService;

    // Constructor
    public CustomerController() {
    }

    // Show customers
    @GetMapping("/customers")
    public String indexPage(Model model) {
        List<Customer> customers = customerService.getCustomers();
        model.addAttribute("customers", customers);
        return "customers/index";
    }

    // Creates a new customer object and return customers/add view
    @GetMapping("/customers/add")
    public String addPage(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customers/add";
    }

    // Save a new customer
    @PostMapping("/customers")
    public String savePage(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }
}
