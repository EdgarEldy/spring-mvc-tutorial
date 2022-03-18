package com.thefreedevelopers.spring_mvc_tutorial.service;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Order;
import com.thefreedevelopers.spring_mvc_tutorial.repository.CustomerRepository;
import com.thefreedevelopers.spring_mvc_tutorial.repository.OrderRepository;
import com.thefreedevelopers.spring_mvc_tutorial.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    // Initialize CustomerRepository
    @Autowired
    private CustomerRepository customerRepository;

    // Initialize ProductRepository
    @Autowired
    private ProductRepository productRepository;

    // Initialize OrderRepository
    @Autowired
    private OrderRepository orderRepository;

    // Get all orders
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    // Save a new order
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    // Get an order by id
    public Order getOrderById(long id){
        Optional<Order> orderOptional = orderRepository.findById(id);
        Order order = null;
        if(orderOptional.isPresent()){
            order = orderOptional.get();
        }
        else {
            throw new RuntimeException("Order not found:" +id);
        }
        return order;
    }

    // Delete an order
    public void deleteOrder(long id){
        orderRepository.deleteById(id);
    }
}
