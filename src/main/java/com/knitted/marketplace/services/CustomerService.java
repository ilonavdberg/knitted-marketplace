package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.auth.RegistrationRequestDto;
import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.mappers.AuthMapper;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.repositories.CustomerRepository;
import com.knitted.marketplace.security.JwtService;
import com.knitted.marketplace.utils.Parser;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final JwtService jwtService;

    public CustomerService(CustomerRepository customerRepository, JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
    }

    public Customer createCustomer(RegistrationRequestDto request) {
        return AuthMapper.toCustomer(request);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerByAuthHeader(String authHeader) {
        System.out.println("start of getCustomerByAuthHeader method");
        String token = Parser.toToken(authHeader);
        System.out.println("Token: " + token);
        Long id = jwtService.extractId(token);
        System.out.println("id: " + id);
        return customerRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
