package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.review.ReviewRequestDto;
import com.knitted.marketplace.dtos.review.ReviewResponseDto;
import com.knitted.marketplace.mappers.ReviewMapper;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.services.ReviewService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("orders/{id}/review")
    public ResponseEntity<ReviewResponseDto> createReview(
            @RequestHeader("Authorization") String authHeader,
            @Valid @PathVariable("id") Long orderId,
            @RequestBody ReviewRequestDto request
    ) {
        Review savedReview = reviewService.save(orderId, request, authHeader);

        ReviewResponseDto response = ReviewMapper.toResponseDto(savedReview);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("shops/{id}/reviews")
    public ResponseEntity<Page<ReviewResponseDto>> getReviewsForShop(
            @PathVariable("id") Long shopId,
            @PageableDefault(size = 12) Pageable pageable
    ) {
        Page<Review> reviewPage = reviewService.getReviewsForShop(shopId, pageable);

        Page<ReviewResponseDto> response = ReviewMapper.toResponseDtoPage(reviewPage);
        return ResponseEntity.ok(response);
    }
}
