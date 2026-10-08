package com.app.Alumno.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.Alumno.dto.EvaluacionDto;
import com.app.Alumno.entity.Evaluacion;
import com.app.Alumno.repository.EvaluacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvaluacionServiceImp implements EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;

    @Override
    public EvaluacionDto crear(EvaluacionDto dto) {
        Evaluacion e = new Evaluacion();
        e.setTipo(dto.getTipo());
        e.setDescripcion(dto.getDescripcion());
        e.setFecha(dto.getFecha());
        e.setCursoId(dto.getCursoId());
        e.setDocenteId(dto.getDocenteId());
        return toDto(evaluacionRepository.save(e));
    }

    @Override
    public List<EvaluacionDto> listarPorCurso(Long cursoId) {
        return evaluacionRepository.findByCursoId(cursoId)
                .stream().map(this::toDto).toList();
    }

    private EvaluacionDto toDto(Evaluacion e) {
        return new EvaluacionDto(e.getId(), e.getTipo(), e.getDescripcion(),
                e.getFecha(), e.getCursoId(), e.getDocenteId());
    }
}
