package com.escuela.security.controller;

import com.escuela.security.dto.AuthRequest;
import com.escuela.security.dto.AuthResponse;
import com.escuela.security.service.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = jwtProvider.createToken(request.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}