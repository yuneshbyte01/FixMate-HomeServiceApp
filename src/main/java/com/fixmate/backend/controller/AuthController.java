package com.fixmate.backend.controller;

import com.fixmate.backend.model.User;
import com.fixmate.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }

        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("Login successful!");
    }

    @GetMapping("/test-user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> testUser() {
        return ResponseEntity.ok("Hello USER 🚀");
    }

    @GetMapping("/test-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> testAdmin() {
        return ResponseEntity.ok("Hello ADMIN 🔐");
    }
}
