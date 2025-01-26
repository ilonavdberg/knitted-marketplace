package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.auth.CustomerResponseDto;
import com.knitted.marketplace.models.Customer;

public class CustomerMapper {
    public static CustomerResponseDto toResponseDto(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                customer.getUser().getUsername(),
                customer.getFirstName(),
                customer.getLastName(),
//                customer.getAddress(),
                customer.getEmail(),
                customer.getPhone()
        );
    }
}
