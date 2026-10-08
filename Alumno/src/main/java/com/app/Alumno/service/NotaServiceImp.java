package com.app.Alumno.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.Alumno.dto.NotaDto;
import com.app.Alumno.entity.Alumno;
import com.app.Alumno.entity.Evaluacion;
import com.app.Alumno.entity.Nota;
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
    public NotaDto crear(NotaDto dto) {
        Evaluacion evaluacion = evaluacionRepository.findById(dto.getEvaluacionId())
                .orElseThrow(() -> new RuntimeException(
                        "Evaluación no encontrada con id: " + dto.getEvaluacionId()));
        Alumno alumno = alumnoRepository.findById(dto.getAlumnoId())
                .orElseThrow(() -> new RuntimeException(
                        "Alumno no encontrado con id: " + dto.getAlumnoId()));

        Nota n = new Nota();
        n.setValor(dto.getValor());
        n.setEvaluacion(evaluacion);
        n.setAlumno(alumno);
        return toDto(notaRepository.save(n));
    }

    @Override
    public List<NotaDto> listarPorEvaluacion(Long evaluacionId) {
        return notaRepository.findByEvaluacionId(evaluacionId)
                .stream().map(this::toDto).toList();
    }

    @Override
    public NotaDto modificar(Long id, NotaDto dto) {
        Nota n = buscar(id);
        n.setValor(dto.getValor());
        return toDto(notaRepository.save(n));
    }

    @Override
    public void eliminar(Long id) {
        notaRepository.delete(buscar(id));
    }

    private Nota buscar(Long id) {
        return notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con id: " + id));
    }

    private NotaDto toDto(Nota n) {
        return new NotaDto(n.getId(), n.getValor(),
                n.getEvaluacion().getId(), n.getAlumno().getId());
    }
}