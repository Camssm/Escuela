package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController { // <-- Debe ser 'class', no '@interface'

    @GetMapping("/holaCursos") // <-- Esta es la URL de tu endpoint
    public String decirHola() {
        return "¡Hola Mundo desde el microservicio Cursos!";
    }
}
