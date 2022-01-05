package com.thefreedevelopers.spring_mvc_tutorial.controller;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Category;
import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;
import com.thefreedevelopers.spring_mvc_tutorial.service.CategoryService;
import com.thefreedevelopers.spring_mvc_tutorial.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    // Initialize ProductService
    @Autowired
    private ProductService productService;

    // Initialize CategoryService
    @Autowired
    private CategoryService categoryService;

    // Show products api
    @GetMapping("/products")
    public String indexPage(Model model){
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return "products/index";
    }

    // Get products/add view with categories
    @GetMapping("products/add")
    public String addPage(Model model){
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        Product product = new Product();
        model.addAttribute("product", product);
        return "products/add";
    }

    @PostMapping("/products")
    public String savePage(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }
}
