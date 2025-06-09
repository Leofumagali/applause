package com.applause.backend.controller;

import com.applause.backend.DTO.LoginRequest;
import com.applause.backend.DTO.RegisterRequest;
import com.applause.backend.DTO.UserResponse;
import com.applause.backend.model.User;
import com.applause.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.function.Function;


@RestController
@RequestMapping("/users")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUser() {
        return userService.findAll()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail()))
                .toList();}

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        var result = userService.findById(id);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var user = result.get();
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getName(), user.getEmail()));

    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequest request) {
        try {
            User user = userService.register(request.name(), request.email(), request.password());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("{id}")
                    .buildAndExpand(user.getId())
                    .toUri();
            return ResponseEntity.created(location)
                    .body(new UserResponse(user.getId(), user.getName(), user.getEmail()));

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}





