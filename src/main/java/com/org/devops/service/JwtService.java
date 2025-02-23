package com.org.devops.service;

import com.org.devops.util.JwtConfig;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    public String generateJwt() {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .signWith(JwtConfig.SECRET_KEY)
                .compact();
    }
}
