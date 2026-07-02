package com.edgareldy.spring_mvc_tutorial.service;

import com.edgareldy.spring_mvc_tutorial.dto.CategoryDto;
import com.edgareldy.spring_mvc_tutorial.entity.Category;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.mapper.CategoryMapper;
import com.edgareldy.spring_mvc_tutorial.repository.CategoryRepository;
import com.edgareldy.spring_mvc_tutorial.service.impl.CategoryServiceImpl;
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
class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryServiceImpl service;

    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        category = Category.builder().id(1L).categoryName("Electronics").build();
        categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setCategoryName("Electronics");
    }

    @Test
    @DisplayName("getCategories - returns list of DTOs")
    void getCategories_returnsDtoList() {
        when(repository.findAll()).thenReturn(Arrays.asList(category));
        when(mapper.toDto(category)).thenReturn(categoryDto);

        List<CategoryDto> result = service.getCategories();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategoryName()).isEqualTo("Electronics");
        verify(repository).findAll();
    }

    @Test
    @DisplayName("getCategoryById - existing id returns DTO")
    void getCategoryById_existingId_returnsDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(category));
        when(mapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto result = service.getCategoryById(1L);

        assertThat(result.getCategoryName()).isEqualTo("Electronics");
    }

    @Test
    @DisplayName("getCategoryById - non-existing id throws ResourceNotFoundException")
    void getCategoryById_notFound_throwsException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getCategoryById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("saveCategory - maps DTO and saves entity")
    void saveCategory_savesEntity() {
        when(mapper.toEntity(categoryDto)).thenReturn(category);

        service.saveCategory(categoryDto);

        verify(mapper).toEntity(categoryDto);
        verify(repository).save(category);
    }

    @Test
    @DisplayName("updateCategory - existing id updates entity")
    void updateCategory_existingId_updatesEntity() {
        when(repository.findById(1L)).thenReturn(Optional.of(category));

        service.updateCategory(1L, categoryDto);

        verify(mapper).updateEntityFromDto(categoryDto, category);
        verify(repository).save(category);
    }

    @Test
    @DisplayName("updateCategory - non-existing id throws ResourceNotFoundException")
    void updateCategory_notFound_throwsException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateCategory(99L, categoryDto))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("deleteCategory - existing id deletes entity")
    void deleteCategory_existingId_deletes() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteCategory(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteCategory - non-existing id throws ResourceNotFoundException")
    void deleteCategory_notFound_throwsException() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteCategory(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).deleteById(any());
    }
}
