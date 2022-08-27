package com.thefreedevelopers.spring_mvc_tutorial.controller;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Customer;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Order;
import com.thefreedevelopers.spring_mvc_tutorial.service.CategoryService;
import com.thefreedevelopers.spring_mvc_tutorial.service.CustomerService;
import com.thefreedevelopers.spring_mvc_tutorial.service.OrderService;
import com.thefreedevelopers.spring_mvc_tutorial.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderController {

    // Initialize OrderService
    @Autowired
    private OrderService orderService;

    // Initialize CustomerService
    @Autowired
    private CustomerService customerService;

    // Initialize ProductService
    @Autowired
    private ProductService productService;

    // initialize CategoryService
    @Autowired
    private CategoryService categoryService;

    // Show orders with customer and product
    @GetMapping("/orders")
    public String indexPage(Model model) {
        List<Order> orders = orderService.getOrders();
        model.addAttribute("orders", orders);
        return "orders/index";
    }

    // Get orders/add view
    @GetMapping("/orders/add")
    public String addPage(Model model) {
        //Get customers
        List<Customer> customers = customerService.getCustomers();
        model.addAttribute("customers", customers);
        // Create a new order object
        Order order = new Order();
        model.addAttribute("order", order);
        return "orders/add";
    }
}
