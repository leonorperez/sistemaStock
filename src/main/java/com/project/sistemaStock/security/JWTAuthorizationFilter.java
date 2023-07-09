package com.project.sistemaStock.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWTAuthorizationFilter: filtrando solicitud...");

        if (isValidUrl(request)) {
            try {
                String bearerToken = request.getHeader("Authorization");
                if (bearerToken != null && bearerToken.startsWith("Bearer")) {
                    String token = bearerToken.replace("Bearer", "");
                    UsernamePasswordAuthenticationToken userNamePAT = TokenUtils.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(userNamePAT);
                }
                filterChain.doFilter(request, response);
            } catch (RuntimeException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Error de autenticación: " + e.getMessage());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("invalid URL");
        }
    }

    private boolean isValidUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // Validar que la URL comience con "/api/"
        if (!requestURI.startsWith("/api/")) {
            return false;
        }

        // Validar las URLs permitidas para cada método HTTP específico
        if (method.equals("GET")) {
            // Permitir URLs que comiencen con "/api/user/" y tengan un identificador UUID al final
            return requestURI.matches("/api/user/\\w+-\\w+-\\w+-\\w+-\\w+") || requestURI.matches("/api/user/\\w+-\\w+-\\w+-\\w+-\\w+/.*");
        } else if (method.equals("POST")) {
            return requestURI.equals("/api/user/new") || requestURI.equals("/api/login");
        } else if (method.equals("PUT") || method.equals("DELETE")) {
            // Permitir URLs que comiencen con "/api/user/" y tengan un identificador UUID al final
            return requestURI.matches("/api/user/\\w+-\\w+-\\w+-\\w+-\\w+") || requestURI.matches("/api/user/\\w+-\\w+-\\w+-\\w+-\\w+/.*");
        }

        return false;
    }
}
