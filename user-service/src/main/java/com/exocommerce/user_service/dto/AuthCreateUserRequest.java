package com.exocommerce.user_service.dto;

import lombok.Data;

@Data
public class AuthCreateUserRequest {
    private String email;
    private String password;
    private String role;
}
