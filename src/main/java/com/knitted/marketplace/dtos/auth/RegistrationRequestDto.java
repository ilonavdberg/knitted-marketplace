package com.knitted.marketplace.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class RegistrationRequestDto {

    @NotBlank(message = "Username is mandatory.")
    @Size(min = 8, max = 30, message = "Username must be between 8 and 30 characters.")
    private String username;

    @NotBlank(message = "Password is mandatory.")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,20}$",
            message = "Password must contain at least one digit, one uppercase letter, one lowercase letter, and one special character.")
    private String password;

    private MultipartFile uploadedImage;

    @NotBlank(message = "First name is mandatory.")
    private String firstName;

    @NotBlank(message = "Last name is mandatory.")
    private String lastName;

    private String street;
    private String houseNumber;
    private String door;
    private String zip;
    private String city;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email address")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(\\+\\d{2}\\s?|0)\\d{9}$", message = "Invalid phone number")
    private String phone;

    public RegistrationRequestDto(
            String username,
            String password,
            MultipartFile uploadedImage,
            String firstName,
            String lastName,
            String street,
            String houseNumber,
            String door,
            String zipcode,
            String city,
            String email,
            String phone
    ) {
        this.username = username;
        this.password = password;
        this.uploadedImage = uploadedImage;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.houseNumber = houseNumber;
        this.door = door;
        this.zip = zipcode;
        this.city = city;
        this.email = email;
        this.phone = phone;
    }

    //Getters

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public MultipartFile getUploadedImage() {
        return uploadedImage;
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

    public void setUploadedImage(MultipartFile uploadedImage) {
        this.uploadedImage = uploadedImage;
    }
}
