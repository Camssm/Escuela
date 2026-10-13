package com.escuela.security.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.escuela.security.dto.AuthRequest;
import com.escuela.security.dto.AuthResponse;
import com.escuela.security.entity.Usuario;
import com.escuela.security.repository.UsuarioRepository;
import com.escuela.security.service.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para generación de tokens JWT")
public class AuthController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Iniciar sesión para obtener el token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        Optional<Usuario> optUsuario = usuarioRepository.findByUsername(request.getUsername());

        if (optUsuario.isPresent()
                && passwordEncoder.matches(request.getPassword(), optUsuario.get().getPassword())) {

            Usuario usuario = optUsuario.get();
            String token = jwtProvider.createToken(usuario.getUsername(), usuario.getRol());
            return ResponseEntity.ok(new AuthResponse(token));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales incorrectas");
    }
}