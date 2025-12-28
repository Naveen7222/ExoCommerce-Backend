package com.exocommerce.cart_service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtUtils {

    private JwtUtils() {
        // utility class
    }

    public static Long getUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalStateException("Invalid authentication token");
        }

        Object id = jwt.getClaim("id");

        if (id == null) {
            throw new IllegalStateException("User id not found in token");
        }

        return Long.valueOf(id.toString());
    }
}
