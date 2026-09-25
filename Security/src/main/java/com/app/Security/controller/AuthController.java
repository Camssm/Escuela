package com.app.Security.controller;

import com.app.Security.jwt.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final TokenProvider tokenProvider;

    public AuthController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) { // 👈 Cambiado a @RequestBody
        String username = loginData.get("username");
        String password = loginData.get("password");

        // Validação simples em memória para testes
        if ("admin".equals(username) && "1234".equals(password)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
            
            String token = tokenProvider.createToken(authentication, false);
            
            Map<String, String> response = new HashMap<>();
            response.put("id_token", token);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body("credenciales inválidas");
    }
}