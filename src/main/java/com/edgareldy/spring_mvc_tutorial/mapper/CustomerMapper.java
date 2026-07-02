package com.edgareldy.spring_mvc_tutorial.mapper;

import com.edgareldy.spring_mvc_tutorial.dto.CustomerDto;
import com.edgareldy.spring_mvc_tutorial.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer customer);

    Customer toEntity(CustomerDto dto);

    void updateEntityFromDto(CustomerDto dto, @MappingTarget Customer customer);
}
