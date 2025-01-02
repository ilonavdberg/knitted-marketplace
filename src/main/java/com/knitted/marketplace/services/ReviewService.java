package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.ReviewRequestDto;
import com.knitted.marketplace.mappers.ReviewMapper;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.repositories.ReviewRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review save(ReviewRequestDto request) {
        Review review = ReviewMapper.toReview(request);

        if (review.getCreatedDate() == null) {
            review.setCreatedDate(LocalDateTime.now());
        }
        review.setLastModifiedDate(LocalDateTime.now());

        return reviewRepository.save(review);
    }

}
