package com.knitted.marketplace.dtos.review;

import com.knitted.marketplace.models.order.Order;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public class ReviewRequestDto {
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private int rating;

    @NotBlank
    @Size(min = 3, max = 50, message = "Title must be between 6 and 50 characters.")
    private String title;

    @Column(nullable = true)
    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;

    @NotNull
    private Order order;

    public ReviewRequestDto(Order order, int rating, String title, String comment) {
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.order = order;
    }

    // Getters

    public int getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public @NotNull Order getOrder() {
        return order;
    }
}
