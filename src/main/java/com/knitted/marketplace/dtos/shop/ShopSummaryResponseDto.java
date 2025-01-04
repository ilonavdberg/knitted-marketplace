package com.knitted.marketplace.dtos.shop;

import com.knitted.marketplace.dtos.ImageResponseDto;

public record ShopSummaryResponseDto(
        Long id,
        String name,
        String description,
        ImageResponseDto shopPicture,
        Long numberOfReviews,
        Double averageRating
) {

}
