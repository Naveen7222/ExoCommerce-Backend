package com.exocommerce.user_service.service;

import com.exocommerce.user_service.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    // ========================
    // CREATE
    // ========================

    // JSON-only create (used by auth-service)
    UserDTO createUser(UserDTO userDTO);

    // Multipart create (image optional)
    UserDTO createUserOptionalImage(UserDTO userDTO, MultipartFile image);

    // ========================
    // READ
    // ========================

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    // ========================
    // UPDATE
    // ========================

    UserDTO updateUser(Long id, UserDTO userDTO);

    // Update profile image only
    void updateProfileImage(Long userId, MultipartFile image);

    // ========================
    // DELETE
    // ========================

    void deleteUser(Long id);
}
