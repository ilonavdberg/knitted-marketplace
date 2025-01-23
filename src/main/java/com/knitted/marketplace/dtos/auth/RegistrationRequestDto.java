package com.knitted.marketplace.dtos.auth;

public class RegistrationRequestDto {

    private String username;
    private String password;

    private String firstName;
    private String lastName;

    private String street;
    private String houseNumber;
    private String door;
    private String zip;
    private String city;

    private String email;
    private String phone;

    //Getters

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getDoor() {
        return door;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
