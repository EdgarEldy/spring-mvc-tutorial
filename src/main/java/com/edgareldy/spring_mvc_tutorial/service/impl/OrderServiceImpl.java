package com.edgareldy.spring_mvc_tutorial.service.impl;

import com.edgareldy.spring_mvc_tutorial.dto.OrderDto;
import com.edgareldy.spring_mvc_tutorial.entity.Customer;
import com.edgareldy.spring_mvc_tutorial.entity.Order;
import com.edgareldy.spring_mvc_tutorial.entity.Product;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.mapper.OrderMapper;
import com.edgareldy.spring_mvc_tutorial.repository.CustomerRepository;
import com.edgareldy.spring_mvc_tutorial.repository.OrderRepository;
import com.edgareldy.spring_mvc_tutorial.repository.ProductRepository;
import com.edgareldy.spring_mvc_tutorial.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;

    @Override
    public List<OrderDto> getOrders() {
        log.debug("Fetching all orders");
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        log.debug("Fetching order with id: {}", id);
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        return mapper.toDto(order);
    }

    @Override
    @Transactional
    public void saveOrder(OrderDto dto) {
        log.info("Saving order for customer id: {}", dto.getCustomerId());
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", dto.getCustomerId()));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", dto.getProductId()));
        Order order = mapper.toEntity(dto);
        order.setCustomer(customer);
        order.setProduct(product);
        repository.save(order);
    }

    @Override
    @Transactional
    public void updateOrder(Long id, OrderDto dto) {
        log.info("Updating order with id: {}", id);
        Order existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", dto.getCustomerId()));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", dto.getProductId()));
        mapper.updateEntityFromDto(dto, existing);
        existing.setCustomer(customer);
        existing.setProduct(product);
        repository.save(existing);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        log.info("Deleting order with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Order", id);
        }
        repository.deleteById(id);
    }
}
