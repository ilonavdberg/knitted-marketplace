package com.knitted.marketplace.models.item;

import com.knitted.marketplace.models.Contact;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private List<Order> orders;
}
