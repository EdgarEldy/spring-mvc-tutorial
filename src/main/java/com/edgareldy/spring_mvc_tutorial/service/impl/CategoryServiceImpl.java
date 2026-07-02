package com.edgareldy.spring_mvc_tutorial.service.impl;

import com.edgareldy.spring_mvc_tutorial.dto.CategoryDto;
import com.edgareldy.spring_mvc_tutorial.entity.Category;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.mapper.CategoryMapper;
import com.edgareldy.spring_mvc_tutorial.repository.CategoryRepository;
import com.edgareldy.spring_mvc_tutorial.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryDto> getCategories() {
        log.debug("Fetching all categories");
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        log.debug("Fetching category with id: {}", id);
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        return mapper.toDto(category);
    }

    @Override
    @Transactional
    public void saveCategory(CategoryDto dto) {
        log.info("Saving category: {}", dto.getCategoryName());
        Category category = mapper.toEntity(dto);
        repository.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(Long id, CategoryDto dto) {
        log.info("Updating category with id: {}", id);
        Category existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        mapper.updateEntityFromDto(dto, existing);
        repository.save(existing);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        log.info("Deleting category with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Category", id);
        }
        repository.deleteById(id);
    }
}
