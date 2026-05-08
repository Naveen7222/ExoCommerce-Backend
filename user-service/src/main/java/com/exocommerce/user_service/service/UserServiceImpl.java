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
        validateAndSetImage(user, image);

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
        User user = userRepository.findByAuthUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    @Override
    public byte[] getProfileImage(Long userId) {

        User user = userRepository.findByAuthUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getProfileImg() == null) {
            throw new RuntimeException("Profile image not found");
        }

        return user.getProfileImg();
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return mapToDTO(user);
    }

    // ========================
    // UPDATE USER (DATA + OPTIONAL IMAGE)
    // ========================
    @Override
    @Transactional
    public UserDTO updateUserWithOptionalImage(Long id, UserDTO userDTO, MultipartFile image) {

        User existing = userRepository.findByAuthUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setName(userDTO.getName());
        existing.setPhone(userDTO.getPhone());
        existing.setAddress(userDTO.getAddress());

        validateAndSetImage(existing, image);

        User updated = userRepository.save(existing);
        return mapToDTO(updated);
    }

    // ========================
    // UPDATE IMAGE ONLY
    // ========================
    @Override
    @Transactional
    public void updateProfileImage(Long userId, MultipartFile image) {

        User user = userRepository.findByAuthUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        validateAndSetImage(user, image);
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
    // IMAGE VALIDATION (SINGLE SOURCE)
    // ========================
    private void validateAndSetImage(User user, MultipartFile image) {

        if (image == null || image.isEmpty()) return;

        if (image.getContentType() == null ||
                !image.getContentType().startsWith("image/")) {
            throw new RuntimeException("Only image files are allowed");
        }

        if (image.getSize() > 2 * 1024 * 1024) {
            throw new RuntimeException("Image size must be less than 2MB");
        }

        try {
            user.setProfileImg(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image", e);
        }
    }

    // ========================
    // MAPPING METHODS
    // ========================
    private UserDTO mapToDTO(User user) {

        boolean hasProfileImage = user.getProfileImg() != null;
        String profileImageUrl = hasProfileImage
                ? "/users/" + user.getId() + "/profile-image"
                : null;

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getAuthUserId(),
                hasProfileImage,
                profileImageUrl,
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
