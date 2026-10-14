package com.app.Cursos.listener;

import java.util.Objects;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.app.Cursos.entity.Alumno;
import com.app.Cursos.entity.Cursos;
import com.app.Cursos.listener.objects.AlumnoEvent;
import com.app.Cursos.repository.AlumnoRepository;
import com.app.Cursos.repository.CursosRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CursoListener {

    private final CursosRepository cursosRepository;
    private final AlumnoRepository alumnoRepository;

    @RabbitListener(queues = "alumno.eventos.queue")
    @Transactional
    public void recibirAltaAlumno(AlumnoEvent evento) {

        if (evento.getAlumnoId() == null || evento.getCursoId() == null) {
            System.out.println("Evento ignorado: falta alumnoId o cursoId");
            return;
        }

        Optional<Cursos> optCurso =
                cursosRepository.findById(evento.getCursoId());

        if (optCurso.isEmpty()) {
            System.out.println(
                "Evento ignorado: no existe el curso " + evento.getCursoId());
            return;
        }

        Cursos curso = optCurso.get();

        Optional<Alumno> optAlumno =
                alumnoRepository.findById(evento.getAlumnoId());

        if ("BAJA".equalsIgnoreCase(evento.getTipo())) {

            if (optAlumno.isPresent()) {
                Alumno alumno = optAlumno.get();

                if (alumno.getCurso() != null
                        && Objects.equals(alumno.getCurso().getId(), curso.getId())) {
                    alumno.setCurso(null);
                    alumnoRepository.save(alumno);
                }
            }

        } else if ("ALTA".equalsIgnoreCase(evento.getTipo())
                || evento.getTipo() == null) {

            Alumno alumno = optAlumno.orElseGet(
                () -> new Alumno(evento.getAlumnoId()));

            if (alumno.getCurso() != null
                    && Objects.equals(alumno.getCurso().getId(), curso.getId())) {
                System.out.println("El alumno ya está en ese curso");
                return;
            }

            alumno.setCurso(curso);
            alumnoRepository.save(alumno);

        } else {
            System.out.println("Tipo de evento desconocido: " + evento.getTipo());
            return;
        }

        System.out.println(
            "Evento " + evento.getTipo()
            + " | alumno " + evento.getAlumnoId()
            + " | curso " + evento.getCursoId());
    }
}
