package com.app.Administracion.dto;

import org.springframework.stereotype.Component;

import com.app.Administracion.Enum.Cargo;
import com.app.Administracion.entity.Administracion;
import com.app.Administracion.mappers.AdministracionMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdministracionDto implements AdministracionMapper{

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private Cargo cargo;
    
    @Override
    public AdministracionDto toDto() {
        return this;
    }

    @Override
    public Administracion toEntity() {
        return new Administracion(nombre, apellido, dni, email, cargo);
    }
		
		

	}

