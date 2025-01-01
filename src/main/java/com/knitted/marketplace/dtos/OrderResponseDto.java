package com.knitted.marketplace.dtos;

//TODO: add customer when implemented
public record OrderResponseDto(
        Long id,
        String orderStatus,
        String createdDate,
        String closedDate,
        CatalogItemResponseDto soldItem
) {
}
