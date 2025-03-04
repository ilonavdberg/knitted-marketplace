package com.knitted.marketplace.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageFile userPicture;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Contact contact;


    public void addRole(String role) {
        roles.add(role);
    }

    //Getters & Setters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ImageFile getUserPicture() {
        return userPicture;
    }

    public List<String> getRoles() {
        return roles;
    }

    public Contact getContact() {
        return contact;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserPicture(ImageFile userPicture) {
        this.userPicture = userPicture;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
