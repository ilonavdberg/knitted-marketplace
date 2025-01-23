package com.knitted.marketplace.models;

import com.knitted.marketplace.models.order.Order;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends Contact {

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
}
