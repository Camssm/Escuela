package com.escuela.security.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.escuela.security.dto.AuthRequest;
import com.escuela.security.dto.AuthResponse;
import com.escuela.security.dto.UsuarioRequest;
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

    @Operation(summary = "Crear usuario")
    @PostMapping("/usuarios")
    public ResponseEntity<?> crearUsuario(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody UsuarioRequest request) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta el token");
        }

        String rolQuienCrea;
        try {
            rolQuienCrea = jwtProvider.getRol(authHeader.substring(7));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        if (!"ROOT".equals(rolQuienCrea)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo Root puede crear usuarios");
        }

        List<String> rolesValidos = List.of("ROOT", "ADMINISTRATIVO", "DOCENTE", "DIRECTOR", "PRECEPTOR");
        if (!rolesValidos.contains(request.getRol())) {
            return ResponseEntity.badRequest().body("Rol inválido");
        }

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }

        Usuario nuevo = new Usuario();
        nuevo.setUsername(request.getUsername());
        nuevo.setPassword(passwordEncoder.encode(request.getPassword()));
        nuevo.setRol(request.getRol());
        usuarioRepository.save(nuevo);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}