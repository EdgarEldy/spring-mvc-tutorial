package com.thefreedevelopers.spring_mvc_tutorial.service;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Category;
import com.thefreedevelopers.spring_mvc_tutorial.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    //Initialize CategoryRepository
    private CategoryRepository repository;

    //Get all categories
    public List<Category> getCategories() {
        return repository.findAll();
    }

    //Save a category
    public void saveCategory(Category category) {
        repository.save(category);
    }

    //Get a category using id
    public Category getCategoryById(long id) {
        Optional<Category> categoryOptional = repository.findById(id);
        Category category = null;
        if (categoryOptional.isPresent()){
            category = categoryOptional.get();
        }
        else {
            throw new RuntimeException("Category not found:" + id);
        }
        return category;
    }

    //Delete a category
    public void deleteCategory(Long id){
        repository.deleteById(id);
    }
}
