package com.knitted.marketplace.dtos;

public record ImageResponseDto(
        Long id,
        String filename,
        String base64Image
) {
}
