package com.knitted.marketplace.dtos.reaction;

import jakarta.validation.constraints.NotBlank;

public class ReactionRequestDto {

    @NotBlank
    String comment;

    public ReactionRequestDto(String comment) {
        this.comment = comment;
    }

    // Getters

    public @NotBlank String getComment() {
        return comment;
    }
}
