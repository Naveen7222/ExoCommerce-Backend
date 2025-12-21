package com.exocommerce.auth_service.controller;

import com.exocommerce.auth_service.dto.LoginRequest;
import com.exocommerce.auth_service.dto.RegisterRequest;
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

    // ========================
    // REGISTER
    // ========================
    @PostMapping("/register")
    public ResponseEntity<Map<String, Long>> register(
            @RequestBody RegisterRequest request
    ) {
        Long authUserId = authService.register(request);
        return ResponseEntity.ok(
                Map.of("authUserId", authUserId)
        );
    }

    // ========================
    // LOGIN
    // ========================
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody LoginRequest request
    ) {
        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }
}
