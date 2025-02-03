package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.auth.CustomerResponseDto;
import com.knitted.marketplace.dtos.auth.LoginRequestDto;
import com.knitted.marketplace.dtos.auth.LoginResponseDto;
import com.knitted.marketplace.dtos.auth.RegistrationRequestDto;
import com.knitted.marketplace.mappers.CustomerMapper;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.User;
import com.knitted.marketplace.repositories.UserRepository;
import com.knitted.marketplace.security.JwtService;
import com.knitted.marketplace.services.AuthService;
import com.knitted.marketplace.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        String token = authService.authenticateAndGenerateToken(loginRequest);
        User user = userService.getUserByUsername(loginRequest.username());
        Long shopId = userService.getShopIdForUser(user);

        LoginResponseDto response = new LoginResponseDto(token, shopId);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDto> register(
            @Valid @ModelAttribute RegistrationRequestDto request,
            @RequestPart(value = "uploadedImage") MultipartFile uploadedImage) {

        request.setUploadedImage(uploadedImage);

        Customer customer = authService.registerNewUser(request);
        CustomerResponseDto response = CustomerMapper.toResponseDto(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
