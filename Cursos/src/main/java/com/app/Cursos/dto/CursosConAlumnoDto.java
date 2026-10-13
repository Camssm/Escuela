package com.app.Cursos.dto;

import java.util.List;

import lombok.Data;

@Data
public class CursosConAlumnoDto {

    private Long id;
    private Long docenteId;
    private String nombreMaestro;
    private String materia;
    private String horario;
    private int cupo;
    private List<AlumnoDto> alumnos;
}
