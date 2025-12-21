package com.exocommerce.user_service.service;

import com.exocommerce.user_service.dto.UserDTO;
import com.exocommerce.user_service.model.User;
import com.exocommerce.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // ========================
    // CREATE USER (JSON ONLY)
    // ========================
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = mapToEntity(userDTO);
        User saved = userRepository.save(user);
        return mapToDTO(saved);
    }

    // ==========================================
    // CREATE USER (IMAGE OPTIONAL - ATOMIC)
    // ==========================================
    @Override
    @Transactional
    public UserDTO createUserOptionalImage(UserDTO dto, MultipartFile image) {

        User user = mapToEntity(dto);

        if (image != null && !image.isEmpty()) {

            // ✅ Image type validation
            if (image.getContentType() == null ||
                    !image.getContentType().startsWith("image/")) {
                throw new RuntimeException("Only image files are allowed");
            }

            // ✅ Image size limit (2 MB)
            long maxSize = 2 * 1024 * 1024; // 2MB
            if (image.getSize() > maxSize) {
                throw new RuntimeException("Image size must be less than 2MB");
            }

            try {
                user.setProfileImg(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read image", e);
            }
        }

        User saved = userRepository.save(user);
        return mapToDTO(saved);
    }

    // ========================
    // READ OPERATIONS
    // ========================
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    // ========================
    // UPDATE USER (NO IMAGE)
    // ========================
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setName(userDTO.getName());
        existing.setEmail(userDTO.getEmail());
        existing.setPhone(userDTO.getPhone());
        existing.setAddress(userDTO.getAddress());

        User updated = userRepository.save(existing);
        return mapToDTO(updated);
    }

    // ========================
    // UPDATE IMAGE ONLY
    // ========================
    @Override
    public void updateProfileImage(Long userId, MultipartFile image) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (image != null && !image.isEmpty()) {

            if (image.getContentType() == null ||
                    !image.getContentType().startsWith("image/")) {
                throw new RuntimeException("Only image files are allowed");
            }

            long maxSize = 2 * 1024 * 1024; // 2MB
            if (image.getSize() > maxSize) {
                throw new RuntimeException("Image size must be less than 2MB");
            }

            try {
                user.setProfileImg(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read image", e);
            }
        }

        userRepository.save(user);
    }

    // ========================
    // DELETE USER
    // ========================
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    // ========================
    // MAPPING METHODS
    // ========================
    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getAuthUserId(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    private User mapToEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setAuthUserId(dto.getAuthUserId());
        return user;
    }
}
