package com.knitted.marketplace.models;

import jakarta.persistence.*;


@Entity
@Table(name="addresses")
@SuppressWarnings("unused") // fields are not accessed directly in the backend but are serialized and sent to the client
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String houseNumber;
    private String door;
    private String zipcode;
    private String city;

    public Address() {}

    public Address(String street, String houseNumber, String door, String zipcode, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.door = door;
        this.zipcode = zipcode;
        this.city = city;
    }

    public Address(String street, String houseNumber, String zipcode, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.door = "";
        this.zipcode = zipcode;
        this.city = city;
    }
}
