package com.knitted.marketplace.dtos;

public record ImageResponseDto(
        Long id,
        String filename,
        String extension,
        String base64Image
) {
}
