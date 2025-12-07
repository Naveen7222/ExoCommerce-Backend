package com.exocommerce.auth_service.controller;

import com.exocommerce.auth_service.dto.PromoteRequest;
import com.exocommerce.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;

    @PreAuthorize("hasRole('ADMIN')") // only admin can access
    @PutMapping("/promote")
    public ResponseEntity<String> promoteUser(@RequestBody PromoteRequest request) {
        authService.promoteUserToAdmin(request.getName(), request.getEmail());
        return ResponseEntity.ok("User promoted to ADMIN successfully");
    }

}
