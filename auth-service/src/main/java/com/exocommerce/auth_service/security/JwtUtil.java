package com.exocommerce.auth_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours

    private Key getSignKey() {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT secret is not configured properly");
        }

        if (secret.length() < 32) {
            throw new IllegalStateException("JWT secret must be at least 32 characters");
        }

        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // CREATE TOKEN
    public String generateToken(Long userId, String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSignKey())
                .compact();
    }

    // READ EMAIL FROM TOKEN
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // READ ROLE FROM TOKEN
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // VERIFY TOKEN VALID
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // GENERIC CLAIM EXTRACTION
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // INTERNAL CLAIM PARSER
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
