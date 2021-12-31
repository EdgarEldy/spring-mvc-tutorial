package com.thefreedevelopers.spring_mvc_tutorial.service;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;
import com.thefreedevelopers.spring_mvc_tutorial.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // Get a product by id
    public Product getProductById(long id){
        Optional<Product> productOptional = repository.findById(id);
        Product product = null;
        if(productOptional.isPresent()){
            product = productOptional.get();
        }
        else {
            throw new RuntimeException("Product not found:" +id);
        }
        return product;
    }

    // Delete a product
    public void deleteProduct(long id){
        repository.deleteById(id);
    }
}
