package com.knitted.marketplace.dtos.auth;

public record LoginRequestDto(
        String username,
        String password
) {
}
