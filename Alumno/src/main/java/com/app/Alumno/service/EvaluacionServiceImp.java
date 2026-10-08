<<<<<<< HEAD

=======
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
package com.app.Alumno.service;

import java.util.List;

import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.app.Alumno.dto.EvaluacionAgregarDto;
=======
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
import com.app.Alumno.dto.EvaluacionDto;
import com.app.Alumno.entity.Evaluacion;
import com.app.Alumno.repository.EvaluacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvaluacionServiceImp implements EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;

    @Override
<<<<<<< HEAD
    public EvaluacionDto crear(EvaluacionAgregarDto dto) {

        Evaluacion evaluacion = dto.mapperTo();

        return toDto(evaluacionRepository.save(evaluacion));
=======
    public EvaluacionDto crear(EvaluacionDto dto) {
        Evaluacion e = new Evaluacion();
        e.setTipo(dto.getTipo());
        e.setDescripcion(dto.getDescripcion());
        e.setFecha(dto.getFecha());
        e.setCursoId(dto.getCursoId());
        e.setDocenteId(dto.getDocenteId());
        return toDto(evaluacionRepository.save(e));
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
    }

    @Override
    public List<EvaluacionDto> listarPorCurso(Long cursoId) {
        return evaluacionRepository.findByCursoId(cursoId)
<<<<<<< HEAD
                .stream()
                .map(this::toDto)
                .toList();
    }

    private EvaluacionDto toDto(Evaluacion e) {
        return new EvaluacionDto(
                e.getId(),
                e.getTipo(),
                e.getDescripcion(),
                e.getFecha(),
                e.getCursoId(),
                e.getDocenteId()
        );
=======
                .stream().map(this::toDto).toList();
    }

    private EvaluacionDto toDto(Evaluacion e) {
        return new EvaluacionDto(e.getId(), e.getTipo(), e.getDescripcion(),
                e.getFecha(), e.getCursoId(), e.getDocenteId());
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
    }
}
