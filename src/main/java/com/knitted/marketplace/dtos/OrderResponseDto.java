package com.knitted.marketplace.dtos;

import com.knitted.marketplace.dtos.item.CatalogItemResponseDto;


public record OrderResponseDto(
        Long id,
        String orderStatus,
        String createdDate,
        String closedDate,
        CatalogItemResponseDto soldItem,
        Long reviewId
) {
}
