package com.thefreedevelopers.spring_mvc_tutorial.controller;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Category;
import com.thefreedevelopers.spring_mvc_tutorial.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {

    //Initialize CategoryService
    @Autowired
    private CategoryService service;

    @GetMapping("/categories")
    public String indexPage(Model model) {
        List<Category> categories = service.getCategories();
        model.addAttribute("categories", categories);
        return "categories/index";
    }

    @GetMapping("/categories/add")
    public String addPage(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "categories/add";
    }

    @PostMapping("/categories")
    public String savePage(@ModelAttribute("category") Category category) {
        service.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editPage(@PathVariable(value = "id") long id, Model model) {
    //Get category from CategoryService
        Category category = service.getCategoryById(id);
        model.addAttribute("category", category);
        return "categories/edit";
    }
}
