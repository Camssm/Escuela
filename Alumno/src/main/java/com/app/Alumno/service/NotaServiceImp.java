<<<<<<< HEAD

=======
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
package com.app.Alumno.service;

import java.util.List;

<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.app.Alumno.dto.NotaAgregarDto;
=======
import org.springframework.stereotype.Service;

>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
import com.app.Alumno.dto.NotaDto;
import com.app.Alumno.entity.Alumno;
import com.app.Alumno.entity.Evaluacion;
import com.app.Alumno.entity.Nota;
<<<<<<< HEAD
import com.app.Alumno.exception.BusinessException;
=======
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
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
<<<<<<< HEAD
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
=======
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
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
    }

    @Override
    public List<NotaDto> listarPorEvaluacion(Long evaluacionId) {
        return notaRepository.findByEvaluacionId(evaluacionId)
<<<<<<< HEAD
                .stream()
                .map(this::toDto)
                .toList();
=======
                .stream().map(this::toDto).toList();
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
    }

    @Override
    public NotaDto modificar(Long id, NotaDto dto) {
<<<<<<< HEAD
        Nota nota = buscar(id);
        nota.setValor(dto.getValor());

        return toDto(notaRepository.save(nota));
=======
        Nota n = buscar(id);
        n.setValor(dto.getValor());
        return toDto(notaRepository.save(n));
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
    }

    @Override
    public void eliminar(Long id) {
        notaRepository.delete(buscar(id));
    }

    private Nota buscar(Long id) {
        return notaRepository.findById(id)
<<<<<<< HEAD
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
=======
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con id: " + id));
    }

    private NotaDto toDto(Nota n) {
        return new NotaDto(n.getId(), n.getValor(),
                n.getEvaluacion().getId(), n.getAlumno().getId());
    }
}
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
