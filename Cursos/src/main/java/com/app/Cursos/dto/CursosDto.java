package com.app.Cursos.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.app.Cursos.entity.Cursos;
import com.app.Cursos.mappers.CursosMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CursosDto implements CursosMapper{

	private Long id;
	private String materia;
	private String nombreMaestro;
	private Long docenteId;
	private String horario;
    private int cupo;

	private List<Long> alumnos = new ArrayList<>();

	@Override
	public Cursos toEntity() {
	    return new Cursos(materia, nombreMaestro, horario, cupo);
	}
	
    @Override
    public CursosDto toDto() {
        return this;
    }

		

	}

