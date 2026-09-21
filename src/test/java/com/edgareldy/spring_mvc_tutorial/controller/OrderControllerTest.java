package com.edgareldy.spring_mvc_tutorial.controller;

import com.edgareldy.spring_mvc_tutorial.dto.CustomerDto;
import com.edgareldy.spring_mvc_tutorial.dto.OrderDto;
import com.edgareldy.spring_mvc_tutorial.dto.ProductDto;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.service.CustomerService;
import com.edgareldy.spring_mvc_tutorial.service.OrderService;
import com.edgareldy.spring_mvc_tutorial.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private ProductService productService;

    private OrderDto orderDto() {
        OrderDto dto = new OrderDto();
        dto.setId(1L);
        dto.setQty(2);
        dto.setTotal(1999.98);
        dto.setCustomerId(1L);
        dto.setProductId(1L);
        dto.setCustomerName("John Doe");
        dto.setProductName("Laptop");
        return dto;
    }

    private CustomerDto customerDto() {
        CustomerDto dto = new CustomerDto();
        dto.setId(1L);
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setTel("0123456789");
        dto.setEmail("john@example.com");
        dto.setAddress("123 Main St");
        return dto;
    }

    private ProductDto productDto() {
        ProductDto dto = new ProductDto();
        dto.setId(1L);
        dto.setProductName("Laptop");
        dto.setUnitPrice(999.99f);
        dto.setCategoryId(1L);
        return dto;
    }

    @Test
    @DisplayName("GET /orders - returns index view with order list")
    void _01_ShouldReturnIndexView_WhenOrdersAreListed() throws Exception {
        when(orderService.getOrders()).thenReturn(Arrays.asList(orderDto()));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders/index"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    @DisplayName("GET /orders/add - returns add view with dropdowns")
    void _02_ShouldReturnAddViewWithDropdowns_WhenAddPageIsRequested() throws Exception {
        when(customerService.getCustomers()).thenReturn(Arrays.asList(customerDto()));
        when(productService.getProducts()).thenReturn(Arrays.asList(productDto()));

        mockMvc.perform(get("/orders/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders/add"))
                .andExpect(model().attributeExists("order", "customers", "products"));
    }

    @Test
    @DisplayName("POST /orders - valid DTO saves and redirects")
    void _03_ShouldRedirect_WhenOrderDtoIsValid() throws Exception {
        mockMvc.perform(post("/orders")
                        .param("customerId", "1")
                        .param("productId", "1")
                        .param("qty", "2")
                        .param("total", "1999.98"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(orderService).saveOrder(any(OrderDto.class));
    }

    @Test
    @DisplayName("POST /orders - missing customerId returns add view with errors")
    void _04_ShouldReturnAddViewWithErrors_WhenCustomerIdIsMissing() throws Exception {
        when(customerService.getCustomers()).thenReturn(Arrays.asList(customerDto()));
        when(productService.getProducts()).thenReturn(Arrays.asList(productDto()));

        mockMvc.perform(post("/orders")
                        .param("productId", "1")
                        .param("qty", "2")
                        .param("total", "1999.98"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders/add"))
                .andExpect(model().attributeHasFieldErrors("order", "customerId"));

        verify(orderService, never()).saveOrder(any());
    }

    @Test
    @DisplayName("GET /orders/edit/{id} - returns edit view with populated DTO")
    void _05_ShouldReturnEditView_WhenOrderExists() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(orderDto());
        when(customerService.getCustomers()).thenReturn(Arrays.asList(customerDto()));
        when(productService.getProducts()).thenReturn(Arrays.asList(productDto()));

        mockMvc.perform(get("/orders/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders/edit"))
                .andExpect(model().attributeExists("order", "customers", "products"));
    }

    @Test
    @DisplayName("GET /orders/edit/{id} - not found returns 404 view")
    void _06_ShouldReturn404_WhenOrderToEditIsNotFound() throws Exception {
        when(orderService.getOrderById(99L)).thenThrow(new ResourceNotFoundException("Order", 99L));

        mockMvc.perform(get("/orders/edit/99"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"));
    }

    @Test
    @DisplayName("POST /orders/edit/{id} - valid DTO updates and redirects")
    void _07_ShouldRedirect_WhenUpdatedOrderDtoIsValid() throws Exception {
        mockMvc.perform(post("/orders/edit/1")
                        .param("customerId", "1")
                        .param("productId", "1")
                        .param("qty", "3")
                        .param("total", "2999.97"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"));

        verify(orderService).updateOrder(eq(1L), any(OrderDto.class));
    }

    @Test
    @DisplayName("POST /orders/delete/{id} - deletes and redirects")
    void _08_ShouldRedirect_WhenOrderIsDeleted() throws Exception {
        mockMvc.perform(post("/orders/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(orderService).deleteOrder(1L);
    }
}
