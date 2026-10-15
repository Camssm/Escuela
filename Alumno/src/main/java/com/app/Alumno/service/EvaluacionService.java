package com.app.Alumno.service;

import java.util.List;

import com.app.Alumno.dto.EvaluacionAgregarDto;
import com.app.Alumno.dto.EvaluacionDto;

public interface EvaluacionService {

	EvaluacionDto crear(EvaluacionAgregarDto dto, String username);

	List<EvaluacionDto> listarPorCurso(Long cursoId);
}