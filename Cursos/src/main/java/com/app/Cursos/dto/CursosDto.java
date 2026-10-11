package com.app.Cursos.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CursosDto {

    private Long id;
    private Long docenteId;
    private String nombreMaestro;
    private String materia;
    private String horario;
    private int cupo;
}