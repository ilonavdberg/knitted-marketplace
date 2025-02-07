package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.auth.RegistrationRequestDto;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.User;


public class AuthMapper {
    public static User toUser(RegistrationRequestDto request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        ImageFile image = request.getUploadedImage() != null ? ImageMapper.toImage(request.getUploadedImage()) : null;
        user.setUserPicture(image);

        return user;
    }

    public static Customer toCustomer(RegistrationRequestDto request) {
        Customer customer = new Customer();

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        if (request.getDoor() != null) {
            customer.setAddress(request.getStreet(), request.getHouseNumber(), request.getDoor(), request.getZip(), request.getCity());
        } else {
            customer.setAddress(request.getStreet(), request.getHouseNumber(), request.getZip(), request.getCity());
        }
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        return customer;
    }
}
