package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.auth.LoginRequestDto;
import com.knitted.marketplace.dtos.auth.RegistrationRequestDto;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.User;
//import com.knitted.marketplace.security.CustomPrincipal;
import com.knitted.marketplace.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final CustomerService customerService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService, CustomerService customerService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.customerService = customerService;
    }

    public String authenticateAndGenerateToken(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.getUserByUsername(username);

        return jwtService.generateToken(user, user.getRoles());
    }

    public Customer registerNewUser(RegistrationRequestDto request) {
        User user = userService.createUser(request);
        Customer customer = customerService.createCustomer(request);
        customer.setUser(user);
        return customer;
    }
}
