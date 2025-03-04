package com.knitted.marketplace.models.order;

import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.Review;
import com.knitted.marketplace.models.item.Item;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrderStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime closedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item soldItem;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne(mappedBy = "order")
    private Review review;

    // Constructors

    public Order() {}

    public Order(Long id, OrderStatus status, LocalDateTime createdDate, LocalDateTime closedDate, Item soldItem, Customer customer, Review review) {
        this.id = id;
        this.status = status;
        this.createdDate = createdDate;
        this.closedDate = closedDate;
        this.soldItem = soldItem;
        this.customer = customer;
        this.review = review;
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public Item getSoldItem() {
        return soldItem;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Review getReview() {
        return review;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public void setSoldItem(Item soldItem) {
        this.soldItem = soldItem;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
