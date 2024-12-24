package com.knitted.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table("customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String firstName;
    String lastName;

    @OneToOne
    Address address;

    String email;
    String phone;
}
