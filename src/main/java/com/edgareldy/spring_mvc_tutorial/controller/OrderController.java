package com.edgareldy.spring_mvc_tutorial.controller;

import com.edgareldy.spring_mvc_tutorial.dto.CustomerDto;
import com.edgareldy.spring_mvc_tutorial.dto.OrderDto;
import com.edgareldy.spring_mvc_tutorial.dto.ProductDto;
import com.edgareldy.spring_mvc_tutorial.service.CustomerService;
import com.edgareldy.spring_mvc_tutorial.service.OrderService;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    @GetMapping
    public String indexPage(Model model) {
        List<OrderDto> orders = orderService.getOrders();
        model.addAttribute("orders", orders);
        return "orders/index";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("order", new OrderDto());
        populateDropdowns(model);
        return "orders/add";
    }

    @PostMapping
    public String savePage(@Valid @ModelAttribute("order") OrderDto dto,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateDropdowns(model);
            return "orders/add";
        }
        orderService.saveOrder(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Order saved successfully.");
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        populateDropdowns(model);
        return "orders/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePage(@PathVariable Long id,
                             @Valid @ModelAttribute("order") OrderDto dto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateDropdowns(model);
            return "orders/edit";
        }
        orderService.updateOrder(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Order updated successfully.");
        return "redirect:/orders";
    }

    @PostMapping("/delete/{id}")
    public String deletePage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        orderService.deleteOrder(id);
        redirectAttributes.addFlashAttribute("successMessage", "Order deleted successfully.");
        return "redirect:/orders";
    }

    private void populateDropdowns(Model model) {
        List<CustomerDto> customers = customerService.getCustomers();
        List<ProductDto> products = productService.getProducts();
        model.addAttribute("customers", customers);
        model.addAttribute("products", products);
    }
}

