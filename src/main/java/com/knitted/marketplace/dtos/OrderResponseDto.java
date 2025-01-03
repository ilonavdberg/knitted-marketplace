package com.knitted.marketplace.dtos;

import com.knitted.marketplace.dtos.item.CatalogItemResponseDto;

//TODO: add customer when implemented
public record OrderResponseDto(
        Long id,
        String orderStatus,
        String createdDate,
        String closedDate,
        CatalogItemResponseDto soldItem
) {
}
