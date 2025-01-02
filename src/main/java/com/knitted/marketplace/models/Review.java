package com.knitted.marketplace.models;

import com.knitted.marketplace.models.order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    @NotNull
    Order order;


    //TODO: add @NotNull and nullable = false when users are implemented
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    Customer author;

    private int rating;
    private String title;
    private String comment;

    @NotNull
    private LocalDateTime createdDate;

    @NotNull
    private LocalDateTime lastModifiedDate;

    @OneToOne(mappedBy = "review")
    Reaction reaction;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public @NotNull Order getOrder() {
        return order;
    }

    public Customer getAuthor() {
        return author;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public int getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public @NotNull LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setOrder(@NotNull Order order) {
        this.order = order;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedDate(@NotNull LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
