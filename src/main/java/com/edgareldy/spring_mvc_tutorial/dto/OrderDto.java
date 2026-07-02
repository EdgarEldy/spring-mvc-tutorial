package com.edgareldy.spring_mvc_tutorial.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class OrderDto {

    private Long id;

    @NotNull
    @Min(1)
    private Integer qty;

    @NotNull
    @Positive
    private Double total;

    @NotNull
    private Long customerId;

    @NotNull
    private Long productId;

    // Read-only display fields populated by the mapper
    private String customerName;
    private String productName;
    private Float unitPrice;
}
