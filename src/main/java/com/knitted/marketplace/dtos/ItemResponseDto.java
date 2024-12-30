package com.knitted.marketplace.dtos;

import java.util.List;

//TODO: add OrderResponseDto in this response
public record ItemResponseDto(
        Long id,
        String title,
        String description,
        Double price,
        String status,
        ShopResponseDto shop,
        String category,
        String subcategory,
        String targetGroup,
        String size,
        List<String> filenames
) {
}
