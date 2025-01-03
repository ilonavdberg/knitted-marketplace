package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.review.ReviewRequestDto;
import com.knitted.marketplace.dtos.review.ReviewResponseDto;
import com.knitted.marketplace.mappers.ReviewMapper;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.services.OrderService;
import com.knitted.marketplace.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService, OrderService orderService) {
        this.reviewService = reviewService;
    }

    @PostMapping("orders/{id}/review")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable("id") Long orderId, @RequestBody ReviewRequestDto request) {
        Review savedReview = reviewService.save(orderId, request);

        ReviewResponseDto response = ReviewMapper.toResponseDto(savedReview);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
