package com.knitted.marketplace.models;

import com.knitted.marketplace.models.order.Order;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    Order order;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    Customer author;

    @OneToOne(mappedBy = "review")
    Reaction reaction;

    private int rating;
    private String title;
    private String comment;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
