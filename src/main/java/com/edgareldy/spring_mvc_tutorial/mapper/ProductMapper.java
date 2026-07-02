package com.edgareldy.spring_mvc_tutorial.mapper;

import com.edgareldy.spring_mvc_tutorial.dto.ProductDto;
import com.edgareldy.spring_mvc_tutorial.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.categoryName", target = "categoryName")
    ProductDto toDto(Product product);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(target = "category.categoryName", ignore = true)
    @Mapping(target = "category.products", ignore = true)
    Product toEntity(ProductDto dto);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(target = "category.categoryName", ignore = true)
    @Mapping(target = "category.products", ignore = true)
    void updateEntityFromDto(ProductDto dto, @MappingTarget Product product);
}
