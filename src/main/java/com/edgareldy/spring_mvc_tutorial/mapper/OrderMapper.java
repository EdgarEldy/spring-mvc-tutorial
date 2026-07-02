package com.edgareldy.spring_mvc_tutorial.mapper;

import com.edgareldy.spring_mvc_tutorial.dto.OrderDto;
import com.edgareldy.spring_mvc_tutorial.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(target = "customerName", expression = "java(order.getCustomer().getFirstName() + \" \" + order.getCustomer().getLastName())")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.unitPrice", target = "unitPrice")
    OrderDto toDto(Order order);

    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(target = "customer.firstName", ignore = true)
    @Mapping(target = "customer.lastName", ignore = true)
    @Mapping(target = "customer.tel", ignore = true)
    @Mapping(target = "customer.email", ignore = true)
    @Mapping(target = "customer.address", ignore = true)
    @Mapping(source = "productId", target = "product.id")
    @Mapping(target = "product.productName", ignore = true)
    @Mapping(target = "product.unitPrice", ignore = true)
    @Mapping(target = "product.category", ignore = true)
    Order toEntity(OrderDto dto);

    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(target = "customer.firstName", ignore = true)
    @Mapping(target = "customer.lastName", ignore = true)
    @Mapping(target = "customer.tel", ignore = true)
    @Mapping(target = "customer.email", ignore = true)
    @Mapping(target = "customer.address", ignore = true)
    @Mapping(source = "productId", target = "product.id")
    @Mapping(target = "product.productName", ignore = true)
    @Mapping(target = "product.unitPrice", ignore = true)
    @Mapping(target = "product.category", ignore = true)
    void updateEntityFromDto(OrderDto dto, @MappingTarget Order order);
}
