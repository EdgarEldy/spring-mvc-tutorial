package com.edgareldy.spring_mvc_tutorial.service.impl;

import com.edgareldy.spring_mvc_tutorial.dto.ProductDto;
import com.edgareldy.spring_mvc_tutorial.entity.Category;
import com.edgareldy.spring_mvc_tutorial.entity.Product;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.mapper.ProductMapper;
import com.edgareldy.spring_mvc_tutorial.repository.CategoryRepository;
import com.edgareldy.spring_mvc_tutorial.repository.ProductRepository;
import com.edgareldy.spring_mvc_tutorial.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    @Override
    public List<ProductDto> getProducts() {
        log.debug("Fetching all products");
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        log.debug("Fetching product with id: {}", id);
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        return mapper.toDto(product);
    }

    @Override
    @Transactional
    public void saveProduct(ProductDto dto) {
        log.info("Saving product: {}", dto.getProductName());
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", dto.getCategoryId()));
        Product product = mapper.toEntity(dto);
        product.setCategory(category);
        repository.save(product);
    }

    @Override
    @Transactional
    public void updateProduct(Long id, ProductDto dto) {
        log.info("Updating product with id: {}", id);
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", dto.getCategoryId()));
        mapper.updateEntityFromDto(dto, existing);
        existing.setCategory(category);
        repository.save(existing);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Product", id);
        }
        repository.deleteById(id);
    }
}
