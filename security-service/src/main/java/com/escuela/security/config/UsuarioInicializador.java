
package com.escuela.security.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.escuela.security.entity.Usuario;
import com.escuela.security.repository.UsuarioRepository;

@Configuration
public class UsuarioInicializador {

    @Bean
    CommandLineRunner crearRoot(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (!usuarioRepository.existsByUsername("admin")) {
                Usuario root = new Usuario();

                root.setUsername("admin");
                root.setPassword(passwordEncoder.encode("aro123"));
                root.setRol("ROOT");

                usuarioRepository.save(root);

                System.out.println("Usuario Root creado correctamente.");
            }
        };
    }
}
