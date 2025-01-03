package com.knitted.marketplace.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reactions")
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    Review review;

    @OneToOne
    @JoinColumn(name="author_id", referencedColumnName = "id")
    Contact author;

    String comment;

    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Review getReview() {
        return review;
    }

    public Contact getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public void setAuthor(Contact author) {
        this.author = author;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
