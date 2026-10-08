package com.app.Alumno.dto;

import org.springframework.stereotype.Component;

import com.app.Alumno.Enum.Estado;
import com.app.Alumno.entity.Alumno;
import com.app.Alumno.mappers.AlumnoMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlumnoDto implements AlumnoMapper{

	private Long id;
	private String nombre;
	private String apellido;
	private String gmail;
	private Estado estado;
	private Long cursoId;
    
    @Override
    public AlumnoDto toDto() {
        return this;
    }

    @Override
    public Alumno toEntity() {
        return new Alumno(id, nombre, apellido, gmail, estado, cursoId);
    }
		

	}

