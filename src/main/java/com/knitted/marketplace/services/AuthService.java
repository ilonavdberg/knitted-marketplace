package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.LoginRequestDto;
import com.knitted.marketplace.models.User;
import com.knitted.marketplace.security.CustomPrincipal;
import com.knitted.marketplace.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public String authenticateAndGenerateToken(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        Long userId = customPrincipal.userId();
        User user = userService.getUserById(userId);

        return jwtService.generateToken(user, user.getRoles());
    }
}
