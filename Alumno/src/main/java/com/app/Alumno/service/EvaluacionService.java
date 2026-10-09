package com.app.Alumno.service;

import java.util.List;

import com.app.Alumno.dto.EvaluacionDto;

public interface EvaluacionService {

    EvaluacionDto crear(EvaluacionDto dto);

    List<EvaluacionDto> listarPorCurso(Long cursoId);
}
