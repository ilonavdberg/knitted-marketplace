package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.review.ReviewRequestDto;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.models.item.Item;
import com.knitted.marketplace.models.order.Order;
import com.knitted.marketplace.models.order.OrderStatus;
import com.knitted.marketplace.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private ReviewService reviewService;

    private List<Review> mockReviews;

    @BeforeEach
    void setup() {
        mockReviews = Arrays.asList(
                new Review(1L, 5, "very nice socks"),
                new Review(2L, 4, "comfy sweater")
        );
    }

    @Test
    void canSaveNewReview() {
        // arrange
        String authHeader = "someRandomBearerToken";
        Long orderId = 1L;
        Item item = new Item(1L, "socks");
        Customer customer = new Customer(1L, "John", "Doe");
        Order order = new Order(
                1L,
                OrderStatus.CLOSED,
                LocalDateTime.now(),
                LocalDateTime.now(),
                item,
                customer,
                null);

        ReviewRequestDto request = new ReviewRequestDto(
                order,
                5,
                "very nice socks",
                "these are very nice socks"
        );

        when(orderService.getOrder(orderId)).thenReturn(order);
        when(reviewRepository.save(any(Review.class))).thenReturn(mockReviews.getFirst());
        when(customerService.getCustomerByAuthHeader(authHeader)).thenReturn(customer);

        // act
        Review savedReview = reviewService.save(orderId, request, authHeader);

        // assert
        assertEquals(mockReviews.getFirst(), savedReview);
    }

    @Test
    void cannotSaveReviewIfNotAuthorized() {
        // arrange
        String authHeader = "someRandomBearerToken";
        Long orderId = 1L;
        Item item = new Item(1L, "socks");
        Customer customer = new Customer(1L, "John", "Doe");
        Order order = new Order(
                1L,
                OrderStatus.CLOSED,
                LocalDateTime.now(),
                LocalDateTime.now(),
                item,
                customer,
                null);

        ReviewRequestDto request = new ReviewRequestDto(
                order,
                5,
                "very nice socks",
                "these are very nice socks"
        );

        when(orderService.getOrder(orderId)).thenReturn(order);
        when(customerService.getCustomerByAuthHeader(authHeader)).thenReturn(new Customer(2L, "Jane", "Doe"));

        // act and assert
        assertThrowsExactly(AccessDeniedException.class, () -> {
            reviewService.save(orderId, request, authHeader);
        });
    }

    @Test
    void canGetReviewsForShop() {
        // arrange
        Long shopId = 1L;
        Pageable pageable = PageRequest.of(0, 2);
        Page<Review> reviewPage = new PageImpl<>(mockReviews);

        when(reviewRepository.findByShopId(shopId, pageable)).thenReturn(reviewPage);

        // act
        Page<Review> fetchedReviewPage = reviewService.getReviewsForShop(shopId, pageable);

        // assert
        assertEquals(fetchedReviewPage, reviewPage);
    }

    @Test
    void canGetReview() {
        // arrange
        Long id = 1L;
        when(reviewRepository.findById(id)).thenReturn(Optional.ofNullable(mockReviews.getFirst()));

        // act
        Review fetchedReview = reviewService.getReview(id);

        // assert
        assertEquals(mockReviews.getFirst(), fetchedReview);
    }
}