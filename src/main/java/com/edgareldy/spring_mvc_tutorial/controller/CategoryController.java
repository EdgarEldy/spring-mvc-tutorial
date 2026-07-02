package com.edgareldy.spring_mvc_tutorial.controller;

import com.edgareldy.spring_mvc_tutorial.dto.CategoryDto;
import com.edgareldy.spring_mvc_tutorial.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public String indexPage(Model model) {
        List<CategoryDto> categories = service.getCategories();
        model.addAttribute("categories", categories);
        return "categories/index";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("category", new CategoryDto());
        return "categories/add";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("category") CategoryDto dto,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "categories/add";
        }
        service.saveCategory(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Category saved successfully.");
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("category", service.getCategoryById(id));
        return "categories/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("category") CategoryDto dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "categories/edit";
        }
        service.updateCategory(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Category updated successfully.");
        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.deleteCategory(id);
        redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully.");
        return "redirect:/categories";
    }
}

