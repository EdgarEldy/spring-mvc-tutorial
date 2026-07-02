package com.edgareldy.spring_mvc_tutorial.controller;

import com.edgareldy.spring_mvc_tutorial.dto.CategoryDto;
import com.edgareldy.spring_mvc_tutorial.dto.ProductDto;
import com.edgareldy.spring_mvc_tutorial.service.CategoryService;
import com.edgareldy.spring_mvc_tutorial.service.ProductService;
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
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String indexPage(Model model) {
        List<ProductDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
        return "products/add";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("product") ProductDto dto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories());
            return "products/add";
        }
        productService.saveProduct(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Product saved successfully.");
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", productService.getProductById(id));
        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("product") ProductDto dto,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories());
            return "products/edit";
        }
        productService.updateProduct(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully.");
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully.");
        return "redirect:/products";
    }
}

