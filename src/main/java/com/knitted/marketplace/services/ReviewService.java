package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.review.ReviewRequestDto;
import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.mappers.ReviewMapper;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.models.order.Order;
import com.knitted.marketplace.repositories.ReviewRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderService orderService;
    private final CustomerService customerService;

    public ReviewService(ReviewRepository reviewRepository, OrderService orderService, CustomerService customerService) {
        this.reviewRepository = reviewRepository;
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @Transactional
    public Review save(Long orderId, ReviewRequestDto request, String authHeader) {

        Customer customer = customerService.getCustomerByAuthHeader(authHeader);
        Order order = orderService.getOrder(orderId);

        if (!customer.equals(order.getCustomer())) {
            throw new AccessDeniedException("You are not authorized to perform this action.");
        }

        Review review = ReviewMapper.toReview(order, request);

        review.setCreatedDate(LocalDateTime.now());
        review.setLastModifiedDate(LocalDateTime.now());
        review.setAuthor(customer);

        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public Page<Review> getReviewsForShop(Long shopId, Pageable pageable) {
        return reviewRepository.findByShopId(shopId, pageable);
    }

    public Review getReview(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
