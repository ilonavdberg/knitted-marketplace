package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.review.ReviewRequestDto;
import com.knitted.marketplace.dtos.review.ReviewResponseDto;
import com.knitted.marketplace.mappers.ReviewMapper;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.services.CustomerService;
import com.knitted.marketplace.services.OrderService;
import com.knitted.marketplace.services.ReviewService;
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
    private final CustomerService customerService;

    public ReviewController(ReviewService reviewService, CustomerService customerService) {
        this.reviewService = reviewService;
        this.customerService = customerService;
    }

    @PostMapping("orders/{id}/review")
    public ResponseEntity<ReviewResponseDto> createReview(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("id") Long orderId,
            @RequestBody ReviewRequestDto request
    ) {
        Customer customer = customerService.getCustomerByAuthHeader(authHeader);

        Review savedReview = reviewService.save(orderId, request, customer);

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
