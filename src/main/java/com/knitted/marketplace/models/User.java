package com.knitted.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}
