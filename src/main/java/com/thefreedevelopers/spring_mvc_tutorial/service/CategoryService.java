package com.thefreedevelopers.spring_mvc_tutorial.service;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Category;
import com.thefreedevelopers.spring_mvc_tutorial.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return repository.findById(id).orElse(null);
    }

    //Delete a category
    public void deleteCategory(Long id){
        repository.deleteById(id);
    }
}
