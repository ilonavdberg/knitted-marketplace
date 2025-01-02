package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.ReviewRequestDto;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.services.ReviewService;
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
    public void createReview(@PathVariable("id") Long orderId, @RequestBody ReviewRequestDto request) {
        Review savedReview = reviewService.save(request);
    }
}
