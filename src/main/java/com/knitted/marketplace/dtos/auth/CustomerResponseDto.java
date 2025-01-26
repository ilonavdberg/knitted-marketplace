package com.knitted.marketplace.dtos.auth;

import com.knitted.marketplace.models.Address;
import com.knitted.marketplace.models.order.Order;

import java.util.List;

public record CustomerResponseDto(
        Long id,
        String username,
        String firstName,
        String lastName,
        Address address,
        String email,
        String phone,
        List<Order> orders
) {
}
