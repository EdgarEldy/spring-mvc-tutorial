package com.edgareldy.spring_mvc_tutorial.controller;

import com.edgareldy.spring_mvc_tutorial.dto.CustomerDto;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.service.CustomerService;
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

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

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

    @Test
    @DisplayName("GET /customers - returns index view with customer list")
    void getCustomers_returnsIndexView() throws Exception {
        when(service.getCustomers()).thenReturn(Arrays.asList(customerDto()));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/index"))
                .andExpect(model().attributeExists("customers"));
    }

    @Test
    @DisplayName("GET /customers/add - returns add view with empty DTO")
    void getAddPage_returnsAddView() throws Exception {
        mockMvc.perform(get("/customers/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/add"))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    @DisplayName("POST /customers - valid DTO saves and redirects")
    void postSave_validDto_redirects() throws Exception {
        mockMvc.perform(post("/customers")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("tel", "0123456789")
                        .param("email", "john@example.com")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(service).saveCustomer(any(CustomerDto.class));
    }

    @Test
    @DisplayName("POST /customers - blank firstName returns add view with errors")
    void postSave_blankFirstName_returnsAddViewWithErrors() throws Exception {
        mockMvc.perform(post("/customers")
                        .param("firstName", "")
                        .param("lastName", "Doe")
                        .param("tel", "0123456789")
                        .param("email", "john@example.com")
                        .param("address", "123 Main St"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/add"))
                .andExpect(model().attributeHasFieldErrors("customer", "firstName"));

        verify(service, never()).saveCustomer(any());
    }

    @Test
    @DisplayName("POST /customers - invalid email returns add view with errors")
    void postSave_invalidEmail_returnsAddViewWithErrors() throws Exception {
        mockMvc.perform(post("/customers")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("tel", "0123456789")
                        .param("email", "not-an-email")
                        .param("address", "123 Main St"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/add"))
                .andExpect(model().attributeHasFieldErrors("customer", "email"));

        verify(service, never()).saveCustomer(any());
    }

    @Test
    @DisplayName("GET /customers/edit/{id} - returns edit view with populated DTO")
    void getEditPage_returnsEditView() throws Exception {
        when(service.getCustomerById(1L)).thenReturn(customerDto());

        mockMvc.perform(get("/customers/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/edit"))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    @DisplayName("GET /customers/edit/{id} - not found returns 404 view")
    void getEditPage_notFound_returns404() throws Exception {
        when(service.getCustomerById(99L)).thenThrow(new ResourceNotFoundException("Customer", 99L));

        mockMvc.perform(get("/customers/edit/99"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"));
    }

    @Test
    @DisplayName("POST /customers/edit/{id} - valid DTO updates and redirects")
    void postUpdate_validDto_redirects() throws Exception {
        mockMvc.perform(post("/customers/edit/1")
                        .param("firstName", "Jane")
                        .param("lastName", "Doe")
                        .param("tel", "0987654321")
                        .param("email", "jane@example.com")
                        .param("address", "456 Oak Ave"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));

        verify(service).updateCustomer(eq(1L), any(CustomerDto.class));
    }

    @Test
    @DisplayName("POST /customers/delete/{id} - deletes and redirects")
    void postDelete_redirects() throws Exception {
        mockMvc.perform(post("/customers/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(service).deleteCustomer(1L);
    }
}
