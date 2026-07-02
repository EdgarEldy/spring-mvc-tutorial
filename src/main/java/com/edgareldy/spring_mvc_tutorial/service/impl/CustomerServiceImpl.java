package com.edgareldy.spring_mvc_tutorial.service.impl;

import com.edgareldy.spring_mvc_tutorial.dto.CustomerDto;
import com.edgareldy.spring_mvc_tutorial.entity.Customer;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.mapper.CustomerMapper;
import com.edgareldy.spring_mvc_tutorial.repository.CustomerRepository;
import com.edgareldy.spring_mvc_tutorial.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public List<CustomerDto> getCustomers() {
        log.debug("Fetching all customers");
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        log.debug("Fetching customer with id: {}", id);
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
        return mapper.toDto(customer);
    }

    @Override
    @Transactional
    public void saveCustomer(CustomerDto dto) {
        log.info("Saving customer: {} {}", dto.getFirstName(), dto.getLastName());
        Customer customer = mapper.toEntity(dto);
        repository.save(customer);
    }

    @Override
    @Transactional
    public void updateCustomer(Long id, CustomerDto dto) {
        log.info("Updating customer with id: {}", id);
        Customer existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
        mapper.updateEntityFromDto(dto, existing);
        repository.save(existing);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        log.info("Deleting customer with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Customer", id);
        }
        repository.deleteById(id);
    }
}
