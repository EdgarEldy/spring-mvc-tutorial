package com.edgareldy.spring_mvc_tutorial.service;

import com.edgareldy.spring_mvc_tutorial.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getProducts();

    ProductDto getProductById(Long id);

    void saveProduct(ProductDto dto);

    void updateProduct(Long id, ProductDto dto);

    void deleteProduct(Long id);
}

