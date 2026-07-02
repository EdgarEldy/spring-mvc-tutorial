package com.edgareldy.spring_mvc_tutorial.service;

import com.edgareldy.spring_mvc_tutorial.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategories();

    CategoryDto getCategoryById(Long id);

    void saveCategory(CategoryDto dto);

    void updateCategory(Long id, CategoryDto dto);

    void deleteCategory(Long id);
}

