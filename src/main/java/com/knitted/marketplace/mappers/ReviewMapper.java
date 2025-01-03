package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.ReviewRequestDto;
import com.knitted.marketplace.dtos.ReviewResponseDto;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.models.order.Order;


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
        return new ReviewResponseDto(
                review.getId(),
                review.getRating(),
                review.getTitle(),
                review.getComment(),
                review.getCreatedDate(),
                review.getLastModifiedDate(),
                review.getOrder().getId()
        );
    }

}
