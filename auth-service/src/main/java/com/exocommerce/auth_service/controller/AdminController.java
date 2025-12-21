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

    // ========================
    // PROMOTE USER TO ADMIN
    // ========================
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/promote")
    public ResponseEntity<String> promoteUser(
            @RequestBody PromoteRequest request
    ) {
        authService.promoteUserToAdmin(
                request.getEmail()
        );

        return ResponseEntity.ok(
                "User promoted to ADMIN successfully"
        );
    }
}
