
package com.app.Alumno.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.app.Alumno.dto.NotaAgregarDto;
import com.app.Alumno.dto.NotaDto;
import com.app.Alumno.entity.Alumno;
import com.app.Alumno.entity.Evaluacion;
import com.app.Alumno.entity.Nota;
import com.app.Alumno.exception.BusinessException;
import com.app.Alumno.repository.Alumnorepository;
import com.app.Alumno.repository.EvaluacionRepository;
import com.app.Alumno.repository.NotaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaServiceImp implements NotaService {

    private final NotaRepository notaRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final Alumnorepository alumnoRepository;

    @Override
    public NotaDto crear(NotaAgregarDto dto) {

        Evaluacion evaluacion = evaluacionRepository
                .findById(dto.getEvaluacionId())
                .orElseThrow(() -> new BusinessException(
                        "P-404",
                        HttpStatus.NOT_FOUND,
                        "Evaluación no encontrada con id: "
                                + dto.getEvaluacionId()));

        Alumno alumno = alumnoRepository
                .findById(dto.getAlumnoId())
                .orElseThrow(() -> new BusinessException(
                        "P-404",
                        HttpStatus.NOT_FOUND,
                        "Alumno no encontrado con id: "
                                + dto.getAlumnoId()));

        Nota nota = dto.mapperTo();
        nota.setEvaluacion(evaluacion);
        nota.setAlumno(alumno);

        return toDto(notaRepository.save(nota));
    }

    @Override
    public List<NotaDto> listarPorEvaluacion(Long evaluacionId) {
        return notaRepository.findByEvaluacionId(evaluacionId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public NotaDto modificar(Long id, NotaDto dto) {
        Nota nota = buscar(id);
        nota.setValor(dto.getValor());

        return toDto(notaRepository.save(nota));
    }

    @Override
    public void eliminar(Long id) {
        notaRepository.delete(buscar(id));
    }

    private Nota buscar(Long id) {
        return notaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "P-404",
                        HttpStatus.NOT_FOUND,
                        "Nota no encontrada con id: " + id));
    }

    private NotaDto toDto(Nota nota) {
        return new NotaDto(
                nota.getId(),
                nota.getValor(),
                nota.getEvaluacion().getId(),
                nota.getAlumno().getId()
        );
    }
}
