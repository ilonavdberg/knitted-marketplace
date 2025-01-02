package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.ReviewRequestDto;
import com.knitted.marketplace.models.Review;

import java.time.LocalDateTime;

public class ReviewMapper {

    public static Review toReview(ReviewRequestDto request) {
        Review review = new Review();

        //TODO: add author when user is implemented
        review.setOrder(request.getOrder());
        review.setRating(request.getRating());
        review.setTitle(request.getTitle());
        review.setComment(request.getComment());

        return review;
    }
}
