package com.app.Administracion.dto;

import com.app.Administracion.Enum.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministracionDto {

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private Cargo cargo;
}