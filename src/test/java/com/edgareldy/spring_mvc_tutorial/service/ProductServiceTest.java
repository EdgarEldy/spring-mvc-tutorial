package com.edgareldy.spring_mvc_tutorial.service;

import com.edgareldy.spring_mvc_tutorial.dto.ProductDto;
import com.edgareldy.spring_mvc_tutorial.entity.Category;
import com.edgareldy.spring_mvc_tutorial.entity.Product;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.mapper.ProductMapper;
import com.edgareldy.spring_mvc_tutorial.repository.CategoryRepository;
import com.edgareldy.spring_mvc_tutorial.repository.ProductRepository;
import com.edgareldy.spring_mvc_tutorial.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl service;

    private Category category;
    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        category = Category.builder().id(1L).categoryName("Electronics").build();
        product = Product.builder().id(1L).productName("Laptop").unitPrice(999.99f).category(category).build();
        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setProductName("Laptop");
        productDto.setUnitPrice(999.99f);
        productDto.setCategoryId(1L);
        productDto.setCategoryName("Electronics");
    }

    @Test
    @DisplayName("getProducts - returns list of DTOs")
    void _01_ShouldReturnDtoList_WhenProductsAreRequested() {
        when(repository.findAll()).thenReturn(Arrays.asList(product));
        when(mapper.toDto(product)).thenReturn(productDto);

        List<ProductDto> result = service.getProducts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProductName()).isEqualTo("Laptop");
    }

    @Test
    @DisplayName("getProductById - existing id returns DTO")
    void _02_ShouldReturnDto_WhenProductIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.toDto(product)).thenReturn(productDto);

        ProductDto result = service.getProductById(1L);

        assertThat(result.getProductName()).isEqualTo("Laptop");
    }

    @Test
    @DisplayName("getProductById - non-existing id throws ResourceNotFoundException")
    void _03_ShouldThrowResourceNotFound_WhenProductIdDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getProductById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("saveProduct - resolves category and saves entity")
    void _04_ShouldSaveEntity_WhenCategoryIsResolved() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(mapper.toEntity(productDto)).thenReturn(product);

        service.saveProduct(productDto);

        verify(repository).save(product);
        assertThat(product.getCategory()).isEqualTo(category);
    }

    @Test
    @DisplayName("saveProduct - non-existing category throws ResourceNotFoundException")
    void _05_ShouldThrowResourceNotFound_WhenCategoryDoesNotExist() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.saveProduct(productDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category");
    }

    @Test
    @DisplayName("updateProduct - existing id updates entity")
    void _06_ShouldUpdateEntity_WhenProductIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        service.updateProduct(1L, productDto);

        verify(mapper).updateEntityFromDto(productDto, product);
        verify(repository).save(product);
    }

    @Test
    @DisplayName("updateProduct - non-existing id throws ResourceNotFoundException")
    void _07_ShouldThrowResourceNotFound_WhenUpdatedProductIdDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateProduct(99L, productDto))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("deleteProduct - existing id deletes entity")
    void _08_ShouldDeleteEntity_WhenProductIdExists() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteProduct(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteProduct - non-existing id throws ResourceNotFoundException")
    void _09_ShouldThrowResourceNotFound_WhenDeletedProductIdDoesNotExist() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteProduct(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).deleteById(any());
    }
}
