package com.edgareldy.spring_mvc_tutorial.controller;

import com.edgareldy.spring_mvc_tutorial.dto.CategoryDto;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.service.CategoryService;
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

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService service;

    @Test
    @DisplayName("GET /categories - returns index view with category list")
    void getCategories_returnsIndexView() throws Exception {
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setCategoryName("Electronics");
        when(service.getCategories()).thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/index"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    @DisplayName("GET /categories/add - returns add view with empty DTO")
    void getAddPage_returnsAddView() throws Exception {
        mockMvc.perform(get("/categories/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/add"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    @DisplayName("POST /categories - valid DTO saves and redirects")
    void postSave_validDto_redirects() throws Exception {
        mockMvc.perform(post("/categories")
                        .param("categoryName", "Electronics"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(service).saveCategory(any(CategoryDto.class));
    }

    @Test
    @DisplayName("POST /categories - blank name returns add view with errors")
    void postSave_blankName_returnsAddViewWithErrors() throws Exception {
        mockMvc.perform(post("/categories")
                        .param("categoryName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/add"))
                .andExpect(model().attributeHasFieldErrors("category", "categoryName"));

        verify(service, never()).saveCategory(any());
    }

    @Test
    @DisplayName("GET /categories/edit/{id} - returns edit view with populated DTO")
    void getEditPage_returnsEditView() throws Exception {
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setCategoryName("Electronics");
        when(service.getCategoryById(1L)).thenReturn(dto);

        mockMvc.perform(get("/categories/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/edit"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    @DisplayName("GET /categories/edit/{id} - not found forwards to 404")
    void getEditPage_notFound_returns404() throws Exception {
        when(service.getCategoryById(99L)).thenThrow(new ResourceNotFoundException("Category", 99L));

        mockMvc.perform(get("/categories/edit/99"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"));
    }

    @Test
    @DisplayName("POST /categories/edit/{id} - valid DTO updates and redirects")
    void postUpdate_validDto_redirects() throws Exception {
        mockMvc.perform(post("/categories/edit/1")
                        .param("categoryName", "Updated Name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));

        verify(service).updateCategory(eq(1L), any(CategoryDto.class));
    }

    @Test
    @DisplayName("POST /categories/delete/{id} - deletes and redirects")
    void postDelete_redirects() throws Exception {
        mockMvc.perform(post("/categories/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(service).deleteCategory(1L);
    }
}
