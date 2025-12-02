package com.exocommerce.auth_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "user-service")
public interface UserClient {

    @PostMapping("/users")
    Map<String, Object> createUser(@RequestBody Map<String, Object> userRequest);
}
