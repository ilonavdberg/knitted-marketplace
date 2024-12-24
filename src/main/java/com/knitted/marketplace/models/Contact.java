package com.knitted.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table(name="contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private String email;
    private String phone;

    @OneToOne (mappedBy = "contact")
    private User user;

    //Do not update existing address - always create new Address object
    public void changeAddress(String street, String houseNumber, String door, String zipcode, String city) {
        this.address = new Address(street, houseNumber, door, zipcode, city);
    }
}
