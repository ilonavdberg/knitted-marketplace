package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.reaction.ReactionRequestDto;
import com.knitted.marketplace.dtos.reaction.ReactionResponseDto;
import com.knitted.marketplace.models.Reaction;
import com.knitted.marketplace.models.Review;

public class ReactionMapper {

    public static Reaction toReaction(Review review, ReactionRequestDto request) {
        Reaction reaction = new Reaction();

        //TODO: add author when user is implemented
        reaction.setReview(review);
        reaction.setComment(request.getComment());

        return reaction;
    }

    public static ReactionResponseDto toResponseDto(Reaction reaction) {
        return new ReactionResponseDto(
                reaction.getId(),
                reaction.getReview().getId(),
                reaction.getComment(),
                reaction.getCreatedDate(),
                reaction.getLastModifiedDate()
        );
    }
}
