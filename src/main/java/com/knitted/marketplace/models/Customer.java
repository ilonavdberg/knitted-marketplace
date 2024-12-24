package com.knitted.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String firstName;
    String lastName;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    Address address;

    String email;
    String phone;
}
