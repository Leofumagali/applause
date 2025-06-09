package com.applause.backend.service;

import com.applause.backend.model.Password;
import com.applause.backend.model.User;
import com.applause.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User register (String name, String email, String rawPassword) throws Exception {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new Exception("Email already registered");
        }

        String hash = passwordEncoder.encode(rawPassword);
        Password password = new Password(hash);
        User user = new User(name, email, password);
        return userRepository.save(user);
    }

    public boolean login(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) return false;

        String storedHash = userOptional.get().getPassword();
        return passwordEncoder.matches(rawPassword, storedHash);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
