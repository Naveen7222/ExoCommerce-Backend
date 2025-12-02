package com.exocommerce.auth_service.controller;

import com.exocommerce.auth_service.dto.LoginRequest;
import com.exocommerce.auth_service.dto.RegisterRequest;
import com.exocommerce.auth_service.model.User;
import com.exocommerce.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(Map.of("token", token)); // return JWT token
    }
    @GetMapping("/test")
    public String test() {
        return "Auth Service is working!";
    }
}
