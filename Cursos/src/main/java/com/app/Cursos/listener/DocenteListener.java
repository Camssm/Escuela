package com.app.Cursos.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.app.Cursos.entity.Docente;
import com.app.Cursos.listener.objects.AdministracionEvent;
import com.app.Cursos.repository.DocenteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class DocenteListener {

    private final DocenteRepository docenteRepository;

    @RabbitListener(queues = "administracion.alta.queue")
    public void recibirAltaAdministracion(AdministracionEvent evento) {
        if (!"DOCENTE".equals(evento.getCargo())) {
            return;  
        }
        docenteRepository.save(new Docente(
            evento.getId(), evento.getNombre(),
            evento.getApellido(), evento.getEmail()));
    }
}