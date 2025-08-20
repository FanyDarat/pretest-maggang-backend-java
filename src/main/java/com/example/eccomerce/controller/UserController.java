package com.example.eccomerce.controller;

import com.example.eccomerce.model.User;
import com.example.eccomerce.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // Register user
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    // Login user
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        boolean success = service.login(user.getName(), user.getPassword());
        return success ? "Login successful! Welcome " + user.getName()
                : "Login failed! Name or password incorrect.";
    }

    // Get all users
    @GetMapping
    public Iterable<User> getAllUsers() {
        return service.getAllUsers();
    }
}
