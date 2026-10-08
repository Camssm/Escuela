package com.app.Alumno.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.app.Alumno.Enum.Estado;
import com.app.Alumno.config.RabbitMQConfig;
import com.app.Alumno.dto.AlumnoConCursoDto;
import com.app.Alumno.dto.AlumnoDto;
import com.app.Alumno.dto.CursosDto;
import com.app.Alumno.entity.Alumno;
import com.app.Alumno.mappers.IMapper;
import com.app.Alumno.repository.Alumnorepository;
import com.app.Alumno.senders.AlumnoEvent;

@Service
public class AlumnoServiceImp implements AlumnoService {

    @Autowired
    Alumnorepository alumnorepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Override
    public List<AlumnoDto> listar() throws Exception {

        List<Alumno> registros = alumnorepository.findAll();

        List<AlumnoDto> dtos = new ArrayList<>();

        for (Alumno alumno : registros) {

            AlumnoDto dto = new AlumnoDto();

            dto.setId(alumno.getId());
            dto.setNombre(alumno.getNombre());
            dto.setApellido(alumno.getApellido());
            dto.setGmail(alumno.getGmail());
            dto.setEstado(alumno.getEstado());
            dto.setCursoId(alumno.getCursoId());
            dtos.add(dto);
        }

        return dtos;
    }


    @Override
    public void agregar(IMapper<Alumno> mapper) throws Exception {

        Alumno alumno = mapper.mapperTo();

        if (alumno.getCursoId() == null || alumno.getCursoId() == 0) {
            alumno.setCursoId(null);
            alumno.setEstado(Estado.SINASIGNACION);
        } else {
            alumno.setEstado(Estado.ASIGNADO);
        }

        Alumno nuevoAlumno = alumnorepository.save(alumno);

        if (nuevoAlumno.getCursoId() != null) {

            AlumnoEvent event = new AlumnoEvent();

            event.setAlumnoId(nuevoAlumno.getId());
            event.setCursoId(nuevoAlumno.getCursoId());

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY,
                    event
            );
        }
    }


    @Override
    public AlumnoConCursoDto obtenerConCurso(Long alumnoId) {

        Alumno alumno = alumnorepository.findById(alumnoId)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Alumno no encontrado con id: " + alumnoId
                    )
                );

        AlumnoConCursoDto dto = new AlumnoConCursoDto();

        dto.setId(alumno.getId());
        dto.setNombre(alumno.getNombre());
        dto.setApellido(alumno.getApellido());
        dto.setGmail(alumno.getGmail());
        dto.setEstado(alumno.getEstado());

        if (alumno.getCursoId() != null) {

            try {

                CursosDto curso = restTemplate.getForObject(
                        "http://MS-CURSOS/api/cursos/"
                        + alumno.getCursoId(),
                        CursosDto.class
                );

                dto.setCurso(curso);

            } catch (RestClientException e) {
            }
        }

        return dto;
    }


    @Override
    public void editar(IMapper<Alumno> mapper) throws Exception {

        Alumno datosNuevos = mapper.mapperTo();

        Alumno existente = alumnorepository.findById(datosNuevos.getId())
                .orElseThrow(() ->
                    new RuntimeException(
                        "Alumno no encontrado con id: "
                        + datosNuevos.getId()
                    )
                );

        existente.setNombre(datosNuevos.getNombre());
        existente.setApellido(datosNuevos.getApellido());
        existente.setGmail(datosNuevos.getGmail());
        existente.setCursoId(datosNuevos.getCursoId());

        alumnorepository.save(existente);
    }


    @Override
    public void cambiarEstado(IMapper<Alumno> mapper) throws Exception {

        Alumno datos = mapper.mapperTo();

        Alumno existente = alumnorepository.findById(datos.getId())
                .orElseThrow(() ->
                    new RuntimeException(
                        "Alumno no encontrado con id: "
                        + datos.getId()
                    )
                );

        existente.setEstado(datos.getEstado());

        alumnorepository.save(existente);
    }
}