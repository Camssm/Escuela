package com.app.Alumno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministracionDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
}