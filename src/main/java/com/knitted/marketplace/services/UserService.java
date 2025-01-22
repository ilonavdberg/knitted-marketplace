package com.knitted.marketplace.services;

import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.models.User;
import com.knitted.marketplace.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
