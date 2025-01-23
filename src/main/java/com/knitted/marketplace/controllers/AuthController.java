package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.auth.CustomerResponseDto;
import com.knitted.marketplace.dtos.auth.LoginRequestDto;
import com.knitted.marketplace.dtos.auth.RegistrationRequestDto;
import com.knitted.marketplace.mappers.AuthMapper;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.repositories.UserRepository;
import com.knitted.marketplace.security.JwtService;
import com.knitted.marketplace.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest) {
        String token = authService.authenticateAndGenerateToken(loginRequest);
        System.out.println("Token: " + token);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body("Token generated");
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDto> register(@Valid @RequestBody RegistrationRequestDto request) {
        Customer customer = authService.registerNewUser(request);
        CustomerResponseDto response = AuthMapper.toResponseDto(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
