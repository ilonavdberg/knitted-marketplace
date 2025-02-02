package com.knitted.marketplace.dtos.review;

import com.knitted.marketplace.dtos.auth.CustomerResponseDto;
import com.knitted.marketplace.dtos.reaction.ReactionResponseDto;

import java.time.LocalDateTime;

//TODO: add customer when it is implemented
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
