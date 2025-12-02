package com.exocommerce.user_service.dto;

import lombok.Data;

@Data
public class AuthUserResponse {
    private Long id;
    private String email;
    private String role;
}
