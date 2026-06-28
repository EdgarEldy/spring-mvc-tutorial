package com.edgareldy.spring_mvc_tutorial.repository;

import com.edgareldy.spring_mvc_tutorial.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}