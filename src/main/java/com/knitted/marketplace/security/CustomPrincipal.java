package com.knitted.marketplace.security;

public record CustomPrincipal(
        Long userId,
        Long shopId
) {
}
