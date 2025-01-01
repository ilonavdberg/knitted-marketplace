package com.knitted.marketplace.dtos;

import com.knitted.marketplace.models.ImageFile;

public record CatalogItemResponseDto(
        Long id,
        String title,
        Double price,
        ImageFile itemPhoto,
        String shopName,
        ImageFile shopPicture
) {
}
