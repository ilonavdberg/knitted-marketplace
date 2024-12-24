package com.knitted.marketplace.models;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne (mappedBy = "owner")
    private Shop shop;



    //Do not update existing address - always create new Address object
    public void changeAddress(String street, String houseNumber, String door, String zipcode, String city) {
        this.address = new Address(street, houseNumber, door, zipcode, city);
    }
}
