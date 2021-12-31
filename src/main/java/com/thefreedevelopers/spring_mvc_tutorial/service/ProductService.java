package com.thefreedevelopers.spring_mvc_tutorial.service;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;
import com.thefreedevelopers.spring_mvc_tutorial.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    //Initialize ProductRepository
    private ProductRepository repository;

    // Get all products
    public List<Product> getProducts() {
        return repository.findAll();
    }

    // Save a new product
    public void saveProduct(Product product) {
        repository.save(product);
    }
}
