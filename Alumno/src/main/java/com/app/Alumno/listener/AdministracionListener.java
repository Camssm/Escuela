package com.app.Alumno.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.app.Alumno.entity.Administracion;
import com.app.Alumno.listener.objects.AdministracionEvent;
import com.app.Alumno.repository.AdministracionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class AdministracionListener {

	private final AdministracionRepository administracionRepository;

	@RabbitListener(queues = "administracion.alumno.queue")
	public void recibirAltaAdministracion(AdministracionEvent evento) {
		administracionRepository.save(new Administracion(
				evento.getId(), evento.getNombre(), evento.getApellido(),
				evento.getEmail(), evento.getCargo()));
		System.out.println("se recibio evento de administracion con id " + evento.getId());
	}
}