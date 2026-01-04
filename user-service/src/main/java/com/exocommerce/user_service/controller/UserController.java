package com.exocommerce.user_service.controller;

import com.exocommerce.user_service.dto.UserDTO;
import com.exocommerce.user_service.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    // ========================
    // CREATE USER (PROFILE + OPTIONAL IMAGE)
    // ========================
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<UserDTO> createUserWithImage(
            @RequestPart("user") String userJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {

        UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);

        return ResponseEntity.ok(
                userService.createUserOptionalImage(userDTO, image)
        );
    }

    // ========================
    // READ USERS
    // ========================
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // ========================
    // GET PROFILE IMAGE
    // ========================
    @GetMapping("/{id}/profile-image")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {

        byte[] image = userService.getProfileImage(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(image);
    }

    // ========================
    // UPDATE USER (DATA + OPTIONAL IMAGE)
    // ========================
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<UserDTO> updateUserWithOptionalImage(
            @PathVariable Long id,
            @RequestPart("user") String userJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {

        UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);

        return ResponseEntity.ok(
                userService.updateUserWithOptionalImage(id, userDTO, image)
        );
    }

    // ========================
    // UPDATE PROFILE IMAGE ONLY
    // ========================
    @PutMapping(value = "/{id}/profile-image", consumes = "multipart/form-data")
    public ResponseEntity<String> updateProfileImage(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile image
    ) {
        userService.updateProfileImage(id, image);
        return ResponseEntity.ok("Profile image updated successfully");
    }

    // ========================
    // DELETE USER
    // ========================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
