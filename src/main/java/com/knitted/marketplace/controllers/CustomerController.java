package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.auth.CustomerResponseDto;
import com.knitted.marketplace.mappers.CustomerMapper;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerResponseDto> getCustomer(@RequestHeader("Authorization") String authHeader) {
        System.out.println("getCustomer method is called");
        Customer customer = customerService.getCustomerByAuthHeader(authHeader);
        System.out.println("Customer: " + customer);
        System.out.println("Customer id: " + customer.getId());
        System.out.println("Username: " + customer.getUser().getUsername());
        CustomerResponseDto response = CustomerMapper.toResponseDto(customer);
        return ResponseEntity.ok(response);
    }
}
