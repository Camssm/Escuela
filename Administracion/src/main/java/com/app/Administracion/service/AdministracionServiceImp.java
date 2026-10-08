package com.app.Administracion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Administracion.config.RabbitMQConfig;
import com.app.Administracion.entity.Administracion;
import com.app.Administracion.mappers.IMapper;
import com.app.Administracion.repository.AdministracionRepository;
import com.app.Administracion.senders.AdministracionEvent;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
public class AdministracionServiceImp implements AdministracionService {

    @Autowired
    private AdministracionRepository administracionRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void agregar(IMapper<Administracion> mapper) throws Exception {

        Administracion guardar = mapper.mapperTo();

        guardar = administracionRepository.save(guardar);

        AdministracionEvent event = new AdministracionEvent();

        event.setId((long) guardar.getId());
        event.setNombre(guardar.getNombre());
        event.setApellido(guardar.getApellido());
        event.setEmail(guardar.getEmail());
        event.setCargo(guardar.getCargo().name());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }

    @Override
    public List<Administracion> listar() throws Exception {
        return administracionRepository.findAll();
    }

    @Override
    public Optional<Administracion> obtenerPorId(int id) throws Exception {
        return administracionRepository.findById(id);
    }

    @Override
    public void editar(IMapper<Administracion> mapper) throws Exception {

        Administracion administracion = mapper.mapperTo();

        administracionRepository.save(administracion);
    }
}