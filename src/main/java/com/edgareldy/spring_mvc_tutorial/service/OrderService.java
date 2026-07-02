package com.edgareldy.spring_mvc_tutorial.service;

import com.edgareldy.spring_mvc_tutorial.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getOrders();

    OrderDto getOrderById(Long id);

    void saveOrder(OrderDto dto);

    void updateOrder(Long id, OrderDto dto);

    void deleteOrder(Long id);
}
