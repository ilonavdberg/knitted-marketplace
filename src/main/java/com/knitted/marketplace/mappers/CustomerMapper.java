package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.ImageResponseDto;
import com.knitted.marketplace.dtos.auth.CustomerResponseDto;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.Shop;

public class CustomerMapper {
    public static CustomerResponseDto toResponseDto(Customer customer) {
        Shop shop = customer.getShop();
        ImageResponseDto userPicture = ImageMapper.toResponseDto(customer.getUser().getUserPicture());

        return new CustomerResponseDto(
                customer.getId(),
                customer.getUser().getUsername(),
                userPicture,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getPhone(),
                shop != null ? shop.getId() : null,
                shop != null ? shop.getName() : null
        );
    }
}
