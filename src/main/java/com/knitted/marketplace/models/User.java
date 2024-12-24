package com.knitted.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;
}
