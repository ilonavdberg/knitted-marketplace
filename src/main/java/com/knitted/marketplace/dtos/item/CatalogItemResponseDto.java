package com.knitted.marketplace.dtos.item;

import com.knitted.marketplace.dtos.ImageResponseDto;
import com.knitted.marketplace.models.item.ItemStatus;

public record CatalogItemResponseDto(
        Long id,
        String title,
        Double price,
        ImageResponseDto itemPhoto,
        String shopName,
        ImageResponseDto shopPicture,
        ItemStatus itemStatus
) {
}
