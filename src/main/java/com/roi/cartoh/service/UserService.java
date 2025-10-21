package com.roi.cartoh.service;

import com.roi.cartoh.model.User;
import com.roi.cartoh.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password) {
        if (userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("Username already taken");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    // Add this method for login validation
    public boolean checkCredentials(String username, String rawPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Use passwordEncoder to verify password
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }
}
