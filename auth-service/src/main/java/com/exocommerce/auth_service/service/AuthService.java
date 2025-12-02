package com.exocommerce.auth_service.service;

import com.exocommerce.auth_service.dto.RegisterRequest;
import com.exocommerce.auth_service.model.User;

import java.util.Map;

public interface AuthService
{

    String login(String email, String password);

    Map<String, Object> register(RegisterRequest request);
}
