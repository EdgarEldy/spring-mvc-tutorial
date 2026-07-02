package com.edgareldy.spring_mvc_tutorial.controller;

import com.edgareldy.spring_mvc_tutorial.dto.CategoryDto;
import com.edgareldy.spring_mvc_tutorial.dto.ProductDto;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.service.CategoryService;
import com.edgareldy.spring_mvc_tutorial.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    private CategoryDto categoryDto() {
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setCategoryName("Electronics");
        return dto;
    }

    private ProductDto productDto() {
        ProductDto dto = new ProductDto();
        dto.setId(1L);
        dto.setProductName("Laptop");
        dto.setUnitPrice(999.99f);
        dto.setCategoryId(1L);
        dto.setCategoryName("Electronics");
        return dto;
    }

    @Test
    @DisplayName("GET /products - returns index view with product list")
    void getProducts_returnsIndexView() throws Exception {
        when(productService.getProducts()).thenReturn(Arrays.asList(productDto()));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/index"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    @DisplayName("GET /products/add - returns add view with categories and empty DTO")
    void getAddPage_returnsAddView() throws Exception {
        when(categoryService.getCategories()).thenReturn(Arrays.asList(categoryDto()));

        mockMvc.perform(get("/products/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/add"))
                .andExpect(model().attributeExists("product", "categories"));
    }

    @Test
    @DisplayName("POST /products - valid DTO saves and redirects")
    void postSave_validDto_redirects() throws Exception {
        mockMvc.perform(post("/products")
                        .param("productName", "Laptop")
                        .param("unitPrice", "999.99")
                        .param("categoryId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(productService).saveProduct(any(ProductDto.class));
    }

    @Test
    @DisplayName("POST /products - blank name returns add view with errors")
    void postSave_blankName_returnsAddViewWithErrors() throws Exception {
        when(categoryService.getCategories()).thenReturn(Arrays.asList(categoryDto()));

        mockMvc.perform(post("/products")
                        .param("productName", "")
                        .param("unitPrice", "999.99")
                        .param("categoryId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/add"))
                .andExpect(model().attributeHasFieldErrors("product", "productName"));

        verify(productService, never()).saveProduct(any());
    }

    @Test
    @DisplayName("POST /products - negative price returns add view with errors")
    void postSave_negativePrice_returnsAddViewWithErrors() throws Exception {
        when(categoryService.getCategories()).thenReturn(Arrays.asList(categoryDto()));

        mockMvc.perform(post("/products")
                        .param("productName", "Laptop")
                        .param("unitPrice", "-1")
                        .param("categoryId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/add"))
                .andExpect(model().attributeHasFieldErrors("product", "unitPrice"));

        verify(productService, never()).saveProduct(any());
    }

    @Test
    @DisplayName("GET /products/edit/{id} - returns edit view with populated DTO")
    void getEditPage_returnsEditView() throws Exception {
        when(productService.getProductById(1L)).thenReturn(productDto());
        when(categoryService.getCategories()).thenReturn(Arrays.asList(categoryDto()));

        mockMvc.perform(get("/products/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/edit"))
                .andExpect(model().attributeExists("product", "categories"));
    }

    @Test
    @DisplayName("GET /products/edit/{id} - not found forwards to 404")
    void getEditPage_notFound_returns404() throws Exception {
        when(productService.getProductById(99L)).thenThrow(new ResourceNotFoundException("Product", 99L));

        mockMvc.perform(get("/products/edit/99"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"));
    }

    @Test
    @DisplayName("POST /products/edit/{id} - valid DTO updates and redirects")
    void postUpdate_validDto_redirects() throws Exception {
        mockMvc.perform(post("/products/edit/1")
                        .param("productName", "Updated Laptop")
                        .param("unitPrice", "1299.99")
                        .param("categoryId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        verify(productService).updateProduct(eq(1L), any(ProductDto.class));
    }

    @Test
    @DisplayName("POST /products/delete/{id} - deletes and redirects")
    void postDelete_redirects() throws Exception {
        mockMvc.perform(post("/products/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(productService).deleteProduct(1L);
    }
}
