package com.app.Administracion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Administracion.config.RabbitMQConfig;
import com.app.Administracion.entity.Administracion;
import com.app.Administracion.mappers.AdministracionMapper;
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
    public void agregar(AdministracionMapper administracionMapper) throws Exception {
        
    	Administracion guardar = administracionRepository.save(administracionMapper.toEntity());
        
        AdministracionEvent event = new AdministracionEvent();
        event.setId((long) guardar.getId());
        event.setNombre(guardar.getNombre());
        event.setApellido(guardar.getApellido());
        event.setEmail(guardar.getEmail());
        event.setCargo(guardar.getCargo().name());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event);
    }
    

    @Override
    public List<AdministracionMapper> listar() throws Exception {
        List<Administracion> registros = administracionRepository.findAll();
        List<AdministracionMapper> mappers = new ArrayList<>();
        for (Administracion registro : registros) {
            mappers.add(registro);
        }
        return mappers;
    }
}