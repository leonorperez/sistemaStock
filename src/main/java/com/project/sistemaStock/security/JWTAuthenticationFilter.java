package com.project.sistemaStock.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.project.sistemaStock.dto.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.project.sistemaStock.services.UserService.setUserDto;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials = new AuthCredentials();
        try {
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (IOException e) {

        }

        UsernamePasswordAuthenticationToken userNamePat = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );
        return getAuthenticationManager().authenticate(userNamePat);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String errorMessage;
        if (failed instanceof BadCredentialsException) {
            errorMessage = "Credenciales inválidas. Por favor, verifica tu correo electrónico y contraseña.";
        } else if (failed instanceof DisabledException) {
            errorMessage = "Tu cuenta está deshabilitada. Contacta al administrador.";
        } else {
            errorMessage = "Error de autenticación. Por favor, verifica tus credenciales.";
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", System.currentTimeMillis());
        responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        responseBody.put("error", "Unauthorized");
        responseBody.put("message", errorMessage);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), responseBody);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        String token = TokenUtils.createToken(userDetails.getName(), userDetails.getUsername());

        // Crear un objeto JSON con la información del usuario
        UserDTO userDTO = setUserDto(userDetails.getUser());

        JSONObject responseJson = new JSONObject();

        try {
            JSONObject userJson = new JSONObject(new Gson().toJson(userDTO));


            // Agregar directamente el objeto userDTO al objeto JSON
            responseJson.put("user", userJson);
            responseJson.put("token", "Bearer " + token);

            response.addHeader("Authorization", "Bearer " + token);
            response.setContentType("application/json");
            response.getWriter().write(responseJson.toString());
            response.getWriter().flush();

            super.successfulAuthentication(request, response, chain, authResult);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
