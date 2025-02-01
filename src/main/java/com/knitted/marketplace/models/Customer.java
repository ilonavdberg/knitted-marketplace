package com.knitted.marketplace.models;

import com.knitted.marketplace.models.order.Order;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends Contact {

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    // Constructors
    public Customer() {}

    public Customer(Long id, String firstName, String lastName) {}

    // Getters
    public List<Order> getOrders() {
        return orders;
    }
}
