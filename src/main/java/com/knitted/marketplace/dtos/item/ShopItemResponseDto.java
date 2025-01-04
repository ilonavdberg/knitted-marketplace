package com.knitted.marketplace.dtos.item;

import com.knitted.marketplace.dtos.ImageResponseDto;
import com.knitted.marketplace.models.ImageFile;

public record ShopItemResponseDto(
        Long id,
        String title,
        Double price,
        String status,
        ImageResponseDto photo
) {
}
