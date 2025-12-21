package com.exocommerce.auth_service.service;

import com.exocommerce.auth_service.dto.RegisterRequest;
import com.exocommerce.auth_service.exception.UserNotFoundException;
import com.exocommerce.auth_service.model.Role;
import com.exocommerce.auth_service.model.User;
import com.exocommerce.auth_service.repository.UserRepository;
import com.exocommerce.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ========================
    // REGISTER (AUTH ONLY)
    // ========================
    @Override
    public Long register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User authUser = new User();
        authUser.setEmail(request.getEmail());
        authUser.setPassword(passwordEncoder.encode(request.getPassword()));
        authUser.setRole(Role.USER);

        User saved = userRepository.save(authUser);
        return saved.getId(); // Long
    }

    // ========================
    // LOGIN
    // ========================
    @Override
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

    // ========================
    // ADMIN PROMOTION
    // ========================
    @Override
    public void promoteUserToAdmin(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

}
