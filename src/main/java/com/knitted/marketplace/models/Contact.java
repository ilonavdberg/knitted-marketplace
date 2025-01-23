package com.knitted.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table(name = "contacts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne (mappedBy = "owner")
    private Shop shop;


    @Override
    public String toString() {
        return firstName + " " + lastName;
    }


    //Getters and Setters


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public User getUser() {
        return user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //Do not update existing address - always create new Address object
    public void setAddress(String street, String houseNumber, String door, String zipcode, String city) {
        this.address = new Address(street, houseNumber, door, zipcode, city);
    }

    public void setAddress(String street, String houseNumber, String zipcode, String city) {
        this.address = new Address(street, houseNumber, zipcode, city);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
