package com.applause.backend.config;

import com.applause.backend.model.AuthenticationData;
import com.applause.backend.model.User;
import com.applause.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid AuthenticationData data) throws Exception {
        User user = userService.register("User", data.email(), data.password());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<DatasTokenJWT> login(@RequestBody @Valid AuthenticationData data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new DatasTokenJWT(tokenJWT));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().body("Logout sucessful.");
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }

}
