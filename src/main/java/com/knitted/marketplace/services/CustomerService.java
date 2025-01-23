package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.auth.RegistrationRequestDto;
import com.knitted.marketplace.mappers.AuthMapper;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(RegistrationRequestDto request) {
        Customer customer = AuthMapper.toCustomer(request);
        return customerRepository.save(customer);
    }
}
