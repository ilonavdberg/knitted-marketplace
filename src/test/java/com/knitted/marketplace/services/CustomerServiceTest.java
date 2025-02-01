package com.knitted.marketplace.services;

import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.repositories.CustomerRepository;
import com.knitted.marketplace.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private CustomerService customerService;

    private List<Customer> mockCustomers;

    @BeforeEach
    void setUp() {
        mockCustomers = Arrays.asList(
                new Customer(1L, "John", "Doe"),
                new Customer(2L, "Jane", "Doe")
        );
    }


    @Test
    void canSaveCustomer() {
        // Arrange
        Customer customer = new Customer(1L, "John", "Smith");
        when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        Customer savedCustomer = customerService.saveCustomer(customer);

        // Assert
        assertEquals(customer, savedCustomer);
    }

    @Test
    void canGetCustomerByAuthHeader() {
        //Arrange
        String authHeader = "someRandomBearerToken";
        when(jwtService.extractId(authHeader)).thenReturn(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(mockCustomers.getFirst()));

        //Act
        Customer fetchedCustomer = customerService.getCustomerByAuthHeader(authHeader);

        //Assert
        assertEquals(mockCustomers.getFirst(), fetchedCustomer);
    }
}