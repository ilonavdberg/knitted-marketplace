package com.knitted.marketplace.dtos.auth;

public record LoginResponseDto(
        String token,
        Long shopId
) {
}
