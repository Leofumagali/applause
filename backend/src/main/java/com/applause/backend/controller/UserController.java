package com.applause.backend.controller;


import com.applause.backend.DTO.LoginRequest;
import com.applause.backend.DTO.RegisterRequest;
import com.applause.backend.model.User;
import com.applause.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request.name(), request.email(), request.password());
            return ResponseEntity.ok("User registered successfully: " + user.getEmail());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
            boolean success = userService.login(request.email(), request.password());
            if (success) {
                return ResponseEntity.ok("Login successful!");
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
}





