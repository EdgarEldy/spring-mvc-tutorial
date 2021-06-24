package com.thefreedevelopers.spring_mvc_tutorial.repository;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
