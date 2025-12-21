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

    private String email; // stored only for display; real email lives in Auth Service

    private String phone;

    private String address;

    private Long authUserId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
