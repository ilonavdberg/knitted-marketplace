package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.ReactionRequestDto;
import com.knitted.marketplace.mappers.ReactionMapper;
import com.knitted.marketplace.models.Reaction;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.repositories.ReactionRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ReactionService {
    public final ReactionRepository reactionRepository;
    public final ReviewService reviewService;

    public ReactionService(ReactionRepository reactionRepository, ReviewService reviewService) {
        this.reactionRepository = reactionRepository;
        this.reviewService = reviewService;
    }

    public Reaction save(Long reviewId, ReactionRequestDto request) {
        Review review = reviewService.getReview(reviewId);
        Reaction reaction = ReactionMapper.toReaction(review, request);

        if (reaction.getCreatedDate() == null) {
            reaction.setCreatedDate(LocalDateTime.now());
        }
        reaction.setLastModifiedDate(LocalDateTime.now());

        return reactionRepository.save(reaction);
    }
}
