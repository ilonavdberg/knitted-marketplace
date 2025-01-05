package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.reaction.ReactionRequestDto;
import com.knitted.marketplace.mappers.ReactionMapper;
import com.knitted.marketplace.models.Reaction;
import com.knitted.marketplace.models.Review;

import com.knitted.marketplace.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ReactionService {
    public final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    public ReactionService(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }

    public Reaction save(Long reviewId, ReactionRequestDto request) {
        Review review = reviewService.getReview(reviewId);
        Reaction reaction = ReactionMapper.toReaction(review, request);

        if (reaction.getCreatedDate() == null) {
            reaction.setCreatedDate(LocalDateTime.now());
        }
        reaction.setLastModifiedDate(LocalDateTime.now());

        review.setReaction(reaction);
        Review savedReview = reviewRepository.save(review); // This will also persist the Reaction due to cascading

        return savedReview.getReaction();
    }
}
