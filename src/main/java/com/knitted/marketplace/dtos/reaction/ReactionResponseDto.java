package com.knitted.marketplace.dtos.reaction;

import java.time.LocalDateTime;

public record ReactionResponseDto(
        Long id,
        Long reviewId,
        String comment,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {
}
