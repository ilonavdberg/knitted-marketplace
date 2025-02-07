package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.auth.RegistrationRequestDto;
import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.exception.exceptions.UserNotFoundException;
import com.knitted.marketplace.mappers.AuthMapper;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.User;
import com.knitted.marketplace.repositories.UserRepository;
import com.knitted.marketplace.security.JwtService;
import com.knitted.marketplace.utils.Parser;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User createUser(RegistrationRequestDto request) {
        User user = AuthMapper.toUser(request);
        System.out.println("password: " + user.getPassword());

        user.addRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("encoded password: " + user.getPassword());

        return saveUser(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public User getUserByAuthHeader(String authHeader) {
        System.out.println("Auth Header: " + authHeader);
        String token = Parser.toToken(authHeader);
        System.out.println("Extracted Token: " + token);
        Long userId = jwtService.extractId(token);
        System.out.println("Extracted User ID: " + userId);
        return getUserById(userId);
    }

    public Long getShopIdForUser(User user) {
        Shop shop = user.getContact().getShop();
        return (shop != null) ? shop.getId() : null;
    }
}
