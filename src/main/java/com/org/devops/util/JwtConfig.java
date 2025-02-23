
package com.org.devops.util;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtConfig {
    private static final String SECRET_KEY_HEX = "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c";
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            SECRET_KEY_HEX.getBytes(StandardCharsets.UTF_8)
    );
}

