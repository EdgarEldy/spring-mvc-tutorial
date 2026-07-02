package com.edgareldy.spring_mvc_tutorial.service;

import com.edgareldy.spring_mvc_tutorial.dto.CustomerDto;
import com.edgareldy.spring_mvc_tutorial.entity.Customer;
import com.edgareldy.spring_mvc_tutorial.exception.ResourceNotFoundException;
import com.edgareldy.spring_mvc_tutorial.mapper.CustomerMapper;
import com.edgareldy.spring_mvc_tutorial.repository.CustomerRepository;
import com.edgareldy.spring_mvc_tutorial.service.impl.CustomerServiceImpl;
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
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerServiceImpl service;

    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id(1L).firstName("John").lastName("Doe")
                .tel("0123456789").email("john@example.com").address("123 Main St")
                .build();
        customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setFirstName("John");
        customerDto.setLastName("Doe");
        customerDto.setTel("0123456789");
        customerDto.setEmail("john@example.com");
        customerDto.setAddress("123 Main St");
    }

    @Test
    @DisplayName("getCustomers - returns list of DTOs")
    void getCustomers_returnsDtoList() {
        when(repository.findAll()).thenReturn(Arrays.asList(customer));
        when(mapper.toDto(customer)).thenReturn(customerDto);

        List<CustomerDto> result = service.getCustomers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    @DisplayName("getCustomerById - existing id returns DTO")
    void getCustomerById_existingId_returnsDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(customer));
        when(mapper.toDto(customer)).thenReturn(customerDto);

        CustomerDto result = service.getCustomerById(1L);

        assertThat(result.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    @DisplayName("getCustomerById - non-existing id throws ResourceNotFoundException")
    void getCustomerById_notFound_throwsException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getCustomerById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("saveCustomer - maps and saves entity")
    void saveCustomer_savesEntity() {
        when(mapper.toEntity(customerDto)).thenReturn(customer);

        service.saveCustomer(customerDto);

        verify(repository).save(customer);
    }

    @Test
    @DisplayName("updateCustomer - existing id updates entity")
    void updateCustomer_existingId_updatesEntity() {
        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        service.updateCustomer(1L, customerDto);

        verify(mapper).updateEntityFromDto(customerDto, customer);
        verify(repository).save(customer);
    }

    @Test
    @DisplayName("updateCustomer - non-existing id throws ResourceNotFoundException")
    void updateCustomer_notFound_throwsException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateCustomer(99L, customerDto))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("deleteCustomer - existing id deletes entity")
    void deleteCustomer_existingId_deletes() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteCustomer(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteCustomer - non-existing id throws ResourceNotFoundException")
    void deleteCustomer_notFound_throwsException() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteCustomer(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).deleteById(any());
    }
}
