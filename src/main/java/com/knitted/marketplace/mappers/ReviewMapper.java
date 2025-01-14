package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.reaction.ReactionResponseDto;
import com.knitted.marketplace.dtos.review.ReviewRequestDto;
import com.knitted.marketplace.dtos.review.ReviewResponseDto;
import com.knitted.marketplace.models.Reaction;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.models.order.Order;
import org.springframework.data.domain.Page;


public class ReviewMapper {

    public static Review toReview(Order order, ReviewRequestDto request) {
        Review review = new Review();

        //TODO: add author when user is implemented
        review.setOrder(order);
        review.setRating(request.getRating());
        review.setTitle(request.getTitle());
        review.setComment(request.getComment());

        return review;
    }

    public static ReviewResponseDto toResponseDto(Review review) {
        Reaction reaction = review.getReaction();

        ReactionResponseDto reactionResponseDto = null;
        if (reaction != null) {
            reactionResponseDto = ReactionMapper.toResponseDto(reaction);
        }

        if (reaction == null) {
            System.out.println("Reaction is null");
        }

        return new ReviewResponseDto(
                review.getId(),
                review.getRating(),
                review.getTitle(),
                review.getComment(),
                review.getCreatedDate(),
                review.getLastModifiedDate(),
                review.getOrder().getId(),
                review.getOrder().getSoldItem().getTitle(),
                reactionResponseDto
        );
    }

    public static Page<ReviewResponseDto> toResponseDtoPage(Page<Review> reviewPage) {
        return reviewPage.map(ReviewMapper::toResponseDto);
    }

}
