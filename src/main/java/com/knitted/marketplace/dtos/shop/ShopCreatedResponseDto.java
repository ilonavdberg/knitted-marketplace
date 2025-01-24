package com.knitted.marketplace.dtos.shop;

import com.knitted.marketplace.models.Shop;

public record ShopCreatedResponseDto(
        Shop shop,
        String token
) {
}
