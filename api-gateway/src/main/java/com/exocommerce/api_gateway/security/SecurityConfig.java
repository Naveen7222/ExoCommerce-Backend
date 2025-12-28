package com.exocommerce.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        return http
                // ❌ CSRF not needed for gateway
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // ✅ Enable CORS inside Spring Security
                .cors(cors -> {})

                // ✅ Allow all requests for now (dev phase)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/**").permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }
}
