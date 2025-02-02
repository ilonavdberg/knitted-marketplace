package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.auth.RegistrationRequestDto;
import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.mappers.AuthMapper;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.User;
import com.knitted.marketplace.repositories.CustomerRepository;
import com.knitted.marketplace.security.JwtService;
import com.knitted.marketplace.utils.Parser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final JwtService jwtService;

    public CustomerService(CustomerRepository customerRepository, JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer createCustomer(RegistrationRequestDto request, User user) {
        Customer customer = AuthMapper.toCustomer(request);

        customer.setUser(user);

        return saveCustomer(customer);
    }

    public Customer getCustomerByAuthHeader(String authHeader) {
        String token = Parser.toToken(authHeader);
        Long id = jwtService.extractId(token);
        return customerRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
