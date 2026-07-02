package com.edgareldy.spring_mvc_tutorial.service;

import com.edgareldy.spring_mvc_tutorial.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> getCustomers();

    CustomerDto getCustomerById(Long id);

    void saveCustomer(CustomerDto dto);

    void updateCustomer(Long id, CustomerDto dto);

    void deleteCustomer(Long id);
}
