package com.knitted.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    private String username;
    private String password;

    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "contact_id", referencedColumnName = "id")

    private Contact contact;
}
