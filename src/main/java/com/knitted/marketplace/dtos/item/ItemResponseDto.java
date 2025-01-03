package com.knitted.marketplace.dtos.item;

import com.knitted.marketplace.dtos.shop.ShopResponseDto;
import com.knitted.marketplace.dtos.shop.ShopSummaryResponseDto;

import java.util.List;

//TODO: add OrderResponseDto in this response
public record ItemResponseDto(
        Long id,
        String title,
        String description,
        Double price,
        String status,
        ShopSummaryResponseDto shop,
        String category,
        String subcategory,
        String targetGroup,
        String size,
        List<String> filenames
) {

}
