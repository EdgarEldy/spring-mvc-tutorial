package com.thefreedevelopers.spring_mvc_tutorial.repository;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}