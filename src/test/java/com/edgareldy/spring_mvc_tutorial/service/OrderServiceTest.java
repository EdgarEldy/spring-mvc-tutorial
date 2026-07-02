package com.edgareldy.spring_mvc_tutorial.service;

import com.edgareldy.spring_mvc_tutorial.dto.OrderDto;
import com.edgareldy.spring_mvc_tutorial.entity.Customer;
import com.edgareldy.spring_mvc_tutorial.entity.Order;
import com.edgareldy.spring_mvc_tutorial.entity.Product;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.mapper.OrderMapper;
import com.edgareldy.spring_mvc_tutorial.repository.CustomerRepository;
import com.edgareldy.spring_mvc_tutorial.repository.OrderRepository;
import com.edgareldy.spring_mvc_tutorial.repository.ProductRepository;
import com.edgareldy.spring_mvc_tutorial.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderMapper mapper;

    @InjectMocks
    private OrderServiceImpl service;

    private Customer customer;
    private Product product;
    private Order order;
    private OrderDto orderDto;

    @BeforeEach
    void setUp() {
        customer = Customer.builder().id(1L).firstName("John").lastName("Doe")
                .tel("0123456789").email("john@example.com").address("123 Main St").build();
        product = Product.builder().id(1L).productName("Laptop").unitPrice(999.99f)
                .category(null).build();
        order = Order.builder().id(1L).qty(2).total(1999.98).customer(customer).product(product).build();
        orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setQty(2);
        orderDto.setTotal(1999.98);
        orderDto.setCustomerId(1L);
        orderDto.setProductId(1L);
        orderDto.setCustomerName("John Doe");
        orderDto.setProductName("Laptop");
    }

    @Test
    @DisplayName("getOrders - returns list of DTOs")
    void getOrders_returnsDtoList() {
        when(repository.findAll()).thenReturn(Arrays.asList(order));
        when(mapper.toDto(order)).thenReturn(orderDto);

        List<OrderDto> result = service.getOrders();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCustomerName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("getOrderById - existing id returns DTO")
    void getOrderById_existingId_returnsDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(order));
        when(mapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = service.getOrderById(1L);

        assertThat(result.getQty()).isEqualTo(2);
    }

    @Test
    @DisplayName("getOrderById - non-existing id throws ResourceNotFoundException")
    void getOrderById_notFound_throwsException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getOrderById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("saveOrder - resolves customer and product, saves entity")
    void saveOrder_savesEntity() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.toEntity(orderDto)).thenReturn(order);

        service.saveOrder(orderDto);

        verify(repository).save(order);
        assertThat(order.getCustomer()).isEqualTo(customer);
        assertThat(order.getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName("saveOrder - non-existing customer throws ResourceNotFoundException")
    void saveOrder_customerNotFound_throwsException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.saveOrder(orderDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Customer");
    }

    @Test
    @DisplayName("updateOrder - existing id updates entity")
    void updateOrder_existingId_updatesEntity() {
        when(repository.findById(1L)).thenReturn(Optional.of(order));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        service.updateOrder(1L, orderDto);

        verify(mapper).updateEntityFromDto(orderDto, order);
        verify(repository).save(order);
    }

    @Test
    @DisplayName("updateOrder - non-existing id throws ResourceNotFoundException")
    void updateOrder_notFound_throwsException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateOrder(99L, orderDto))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("deleteOrder - existing id deletes entity")
    void deleteOrder_existingId_deletes() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteOrder(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteOrder - non-existing id throws ResourceNotFoundException")
    void deleteOrder_notFound_throwsException() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteOrder(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).deleteById(any());
    }
}
