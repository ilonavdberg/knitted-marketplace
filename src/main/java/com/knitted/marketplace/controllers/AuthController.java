package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.LoginRequestDto;
import com.knitted.marketplace.repositories.UserRepository;
import com.knitted.marketplace.security.JwtService;
import com.knitted.marketplace.services.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest) {
        String token = authService.authenticateAndGenerateToken(loginRequest);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body("Token generated");
    }

}
