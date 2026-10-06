package com.app.Cursos.listener;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.app.Cursos.entity.Alumno;
import com.app.Cursos.entity.Cursos;
import com.app.Cursos.listener.objects.AlumnoEvent;
import com.app.Cursos.repository.CursosRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class CursoListener {

	
	private final CursosRepository cursosRepository;
	
	
	 @RabbitListener(queues = "alumno.alta.queue")
	 public void recibirAltaAlumno(AlumnoEvent evento) {
		 	Optional<Cursos> optCurso = cursosRepository.findById(evento.getCursoId());
		 	optCurso.get().addAlumno(new Alumno(evento.getAlumnoId()));
		 	cursosRepository.save(optCurso.get());
		 	System.out.println("se recibio evento con id"+ evento.getCursoId() );
	  }
	
	
}