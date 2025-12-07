package com.exocommerce.auth_service.service;

import com.exocommerce.auth_service.client.UserClient;
import com.exocommerce.auth_service.dto.RegisterRequest;
import com.exocommerce.auth_service.exception.UserNotFoundException;
import com.exocommerce.auth_service.model.Role;
import com.exocommerce.auth_service.model.User;
import com.exocommerce.auth_service.repository.UserRepository;
import com.exocommerce.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserClient userClient;

    @Override
    public Map<String, Object> register(RegisterRequest request) {

        User authUser = new User();
        authUser.setName(request.getName());
        authUser.setEmail(request.getEmail());
        authUser.setPassword(passwordEncoder.encode(request.getPassword()));
        authUser.setRole(Role.USER);
        User savedAuthUser = userRepository.save(authUser);

        Map<String, Object> userRequest = Map.of(
                "name", request.getName(),
                "email", request.getEmail(),
                "phone", request.getPhone(),
                "address", request.getAddress(),
                "profileImg", request.getProfileImg(),
                "authUserId", savedAuthUser.getId()
        );


        // 3. Call User Service using FeignClient
        Map<String, Object> userProfile =
                userClient.createUser(userRequest);

        // 4. Return combined response
        return Map.of(
                "message", "Registration successful",
                "authUser", savedAuthUser,
                "profile", userProfile
        );
    }

    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
    @Override
    public void promoteUserToAdmin(String name, String email) {
        User user = userRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

}
