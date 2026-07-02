package com.edgareldy.spring_mvc_tutorial.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200, message = "Product name must be between 2 and 200 characters")
    private String productName;

    @Positive(message = "Unit price must be greater than 0")
    private float unitPrice;

    @NotNull(message = "Category is required")
    private Long categoryId;

    // Populated on read for display purposes
    private String categoryName;
}
