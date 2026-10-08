package com.app.Alumno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursosDto {
	private Long id;
	private String materia;
	private String nombreMaestro;
	private Long docenteId;
	private String horario;
}