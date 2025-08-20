package com.example.eccomerce.service;

import com.example.eccomerce.model.User;
import com.example.eccomerce.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public boolean login(String name, String rawPassword) {
        Optional<User> userOpt = repository.findByName(name);
        return userOpt.map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }

    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }
}
