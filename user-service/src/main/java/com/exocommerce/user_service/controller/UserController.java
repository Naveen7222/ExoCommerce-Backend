package com.exocommerce.user_service.controller;

import com.exocommerce.user_service.dto.UserDTO;
import com.exocommerce.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ========================
    // CREATE USER (PROFILE + OPTIONAL IMAGE)
    // ========================
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<UserDTO> createUserWithImage(
            @RequestPart("user") String userJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        UserDTO userDTO = mapper.readValue(userJson, UserDTO.class);

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

    // ========================
    // UPDATE USER (NO IMAGE)
    // ========================
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserDTO userDTO
    ) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
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
