package com.thefreedevelopers.spring_mvc_tutorial.controller;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;
import com.thefreedevelopers.spring_mvc_tutorial.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    // Initialize ProductService
    @Autowired
    private ProductService productService;

    // Show products api
    @GetMapping("/products")
    public String indexPage(Model model){
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return "products/index";
    }
}
