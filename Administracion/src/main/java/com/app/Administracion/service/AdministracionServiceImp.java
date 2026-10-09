package com.app.Administracion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import com.app.Administracion.exception.BusinessException;
import com.app.Administracion.exception.RequestException;

import com.app.Administracion.config.RabbitMQConfig;
import com.app.Administracion.dto.AdministracionDto;
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

        if (guardar.getCargo() == null) {
            throw new RequestException("P-400", "El cargo es obligatorio");
        }

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
    public List<AdministracionDto> listar() throws Exception {

        List<Administracion> registros = administracionRepository.findAll();

        List<AdministracionDto> dtos = new ArrayList<>();

        for (Administracion administracion : registros) {

            AdministracionDto dto = new AdministracionDto();

            dto.setId(administracion.getId());
            dto.setNombre(administracion.getNombre());
            dto.setApellido(administracion.getApellido());
            dto.setDni(administracion.getDni());
            dto.setEmail(administracion.getEmail());
            dto.setCargo(administracion.getCargo());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public AdministracionDto obtenerPorId(int id) throws Exception {

        Administracion administracion = administracionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "P-404",
                        HttpStatus.NOT_FOUND,
                        "Administrativo no encontrado con id: " + id
                ));

        AdministracionDto dto = new AdministracionDto();

        dto.setId(administracion.getId());
        dto.setNombre(administracion.getNombre());
        dto.setApellido(administracion.getApellido());
        dto.setDni(administracion.getDni());
        dto.setEmail(administracion.getEmail());
        dto.setCargo(administracion.getCargo());

        return dto;
    }

    @Override
    public void editar(IMapper<Administracion> mapper) throws Exception {

    	Administracion administracion = mapper.mapperTo();

    	if (!administracionRepository.existsById(administracion.getId())) {
    	    throw new BusinessException(
    	            "P-404", HttpStatus.NOT_FOUND,
    	            "Administrativo no encontrado con id: " + administracion.getId());
    	}

    	administracionRepository.save(administracion);
    }
}