package com.knitted.marketplace.dtos.shop;

import com.knitted.marketplace.models.ImageFile;

public record ShopSummaryResponseDto(
        Long id,
        String name,
        String description,
        ImageFile shopPicture,
        Long numberOfReviews,
        Double averageRating
) {

}
