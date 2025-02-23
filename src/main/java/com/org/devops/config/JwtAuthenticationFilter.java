package com.org.devops.config;

import com.org.devops.util.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Set<String> USED_JTI = new HashSet<>();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            filterChain.doFilter(request, response); // Continuar sin validar
            return;
        }
        String jwt = request.getHeader("X-JWT-KWY");

        if (jwt == null || jwt.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT no encontrado o no valido");
            return;
        }
            try {
                Claims claims = Jwts.parser()
                        .verifyWith(JwtConfig.SECRET_KEY)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();

                Date expiration = claims.getExpiration();
                if (expiration.before(new Date())) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("JWT ha expirado");
                    return;
                }
                // Verificar que el jti no haya sido usado previamente
                String jti = claims.getId();
                if (USED_JTI.contains(jti)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("JWT transaccion ya ha sido usada");
                    return;
                }
                // Marcar el jti como usado
                USED_JTI.add(jti);

                // Agregar autenticación al contexto de seguridad
                var authentication = new UsernamePasswordAuthenticationToken(
                        "api-key-user",
                        null,
                        Collections.emptyList()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT no valido: " + e.getMessage());
            }
    }
}