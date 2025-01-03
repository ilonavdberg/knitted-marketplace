package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.review.ReviewRequestDto;
import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.mappers.ReviewMapper;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.models.order.Order;
import com.knitted.marketplace.repositories.ReviewRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderService orderService;

    public ReviewService(ReviewRepository reviewRepository, OrderService orderService) {
        this.reviewRepository = reviewRepository;
        this.orderService = orderService;
    }

    @Transactional
    public Review save(Long orderId, ReviewRequestDto request) {
        Order order = orderService.getOrder(orderId);
        Review review = ReviewMapper.toReview(order, request);

        if (review.getCreatedDate() == null) {
            review.setCreatedDate(LocalDateTime.now());
        }
        review.setLastModifiedDate(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    public Review getReview(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

}
