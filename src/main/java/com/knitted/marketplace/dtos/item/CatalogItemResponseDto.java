package com.knitted.marketplace.dtos.item;

import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.item.ItemStatus;

public record CatalogItemResponseDto(
        Long id,
        String title,
        Double price,
        ImageFile itemPhoto,
        String shopName,
        ImageFile shopPicture,
        ItemStatus itemStatus
) {
}
