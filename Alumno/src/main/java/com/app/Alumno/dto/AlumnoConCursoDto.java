package com.app.Alumno.dto;

import com.app.Alumno.Enum.Estado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoConCursoDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String gmail;
    private Estado estado;
    private CursosDto curso;
}