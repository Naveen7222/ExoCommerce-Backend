package com.exocommerce.user_service.client;

import com.exocommerce.user_service.dto.AuthCreateUserRequest;
import com.exocommerce.user_service.dto.AuthUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service")   // name must match eureka service id
public interface AuthFeignClient {

    @PostMapping("/auth/register/internal")
    AuthUserResponse createAuthUser( @RequestBody AuthCreateUserRequest request);
}
