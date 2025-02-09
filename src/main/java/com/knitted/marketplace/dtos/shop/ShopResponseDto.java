package com.knitted.marketplace.dtos.shop;

import com.knitted.marketplace.dtos.ImageResponseDto;

import java.util.List;


public record ShopResponseDto(
        Long id,
        String name,
        String description,
        List<String> items,
        String owner,
        ImageResponseDto shopPicture
) {

}
