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
}
