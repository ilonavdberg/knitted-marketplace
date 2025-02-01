package com.knitted.marketplace.integration;

import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "testuser", roles = {"USER"})
public class GetReviewsForShopTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setup() {
        reviewRepository.deleteAll();

        List<Review> testReviews = List.of(
                new Review(1L, 5, "test review 1", "very good product", LocalDateTime.now(), LocalDateTime.now(), null, null, null),
                new Review(1L, 4, "test review 1", "very good product", LocalDateTime.now(), LocalDateTime.now(), null, null, null)
        );
    }
}
