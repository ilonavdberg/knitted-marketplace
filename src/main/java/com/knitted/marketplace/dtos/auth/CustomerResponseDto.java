package com.knitted.marketplace.dtos.auth;

import com.knitted.marketplace.models.Address;

public record CustomerResponseDto(
        Long id,
        String username,
        String firstName,
        String lastName,
//        Address address,
        String email,
        String phone
) {
}
