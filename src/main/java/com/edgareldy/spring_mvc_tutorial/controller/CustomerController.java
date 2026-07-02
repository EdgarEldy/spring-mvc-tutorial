package com.edgareldy.spring_mvc_tutorial.controller;

import com.edgareldy.spring_mvc_tutorial.dto.CustomerDto;
import com.edgareldy.spring_mvc_tutorial.service.CustomerService;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String indexPage(Model model) {
        List<CustomerDto> customers = customerService.getCustomers();
        model.addAttribute("customers", customers);
        return "customers/index";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("customer", new CustomerDto());
        return "customers/add";
    }

    @PostMapping
    public String savePage(@Valid @ModelAttribute("customer") CustomerDto dto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "customers/add";
        }
        customerService.saveCustomer(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Customer saved successfully.");
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customers/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePage(@PathVariable Long id,
                             @Valid @ModelAttribute("customer") CustomerDto dto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "customers/edit";
        }
        customerService.updateCustomer(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Customer updated successfully.");
        return "redirect:/customers";
    }

    @PostMapping("/delete/{id}")
    public String deletePage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        customerService.deleteCustomer(id);
        redirectAttributes.addFlashAttribute("successMessage", "Customer deleted successfully.");
        return "redirect:/customers";
    }
}

