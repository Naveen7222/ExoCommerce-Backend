package com.exocommerce.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email; // display only
    private String phone;
    private String address;

    private Long authUserId;

    // ✅ NEW (profile image info)
    private boolean hasProfileImage;
    private String profileImageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
