package com.knitted.marketplace.dtos;

import java.time.LocalDateTime;

//TODO: add customer when it is implemented
public record ReviewResponseDto(
        Long id,
        int rating,
        String title,
        String comment,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        Long orderId
) {
}
