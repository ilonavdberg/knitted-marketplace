package com.knitted.marketplace.dtos.review;

import com.knitted.marketplace.dtos.auth.CustomerResponseDto;

import java.time.LocalDateTime;


public record ReviewResponseDto(
        Long id,
        int rating,
        String title,
        String comment,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        Long orderId,
        String itemName,
        CustomerResponseDto customer
) {
}
