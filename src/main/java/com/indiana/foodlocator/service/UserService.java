package com.indiana.foodlocator.service;

import com.indiana.foodlocator.entity.User;
import com.indiana.foodlocator.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                    "An account already exists with that email.");
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword()));

        user.setRole("USER");

        return userRepository.save(user);
    }
}
