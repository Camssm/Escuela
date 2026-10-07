package com.app.Alumno.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Alumno.config.RabbitMQConfig;
import com.app.Alumno.dto.AlumnoConCursoDto;
import com.app.Alumno.entity.Alumno;
import com.app.Alumno.mappers.AlumnoMapper;
import com.app.Alumno.repository.Alumnorepository;
import com.app.Alumno.senders.AlumnoEvent;

@Service
public class AlumnoServiceImp implements AlumnoService {
	
	@Autowired
	Alumnorepository alumnorepository;

	@Autowired
    RabbitTemplate rabbitTemplate;	

	@Override
	public List<AlumnoMapper> listar() throws Exception {
		List<Alumno> registros = alumnorepository.findAll();
        List<AlumnoMapper> mappers = new ArrayList<>();
        for (Alumno registro : registros) {
            mappers.add(registro);
        }
        return mappers;
    }

	@Override
	public void agregar(AlumnoMapper alumnoMapper) throws Exception {

	    Alumno nuevoAlumno = alumnorepository.save(alumnoMapper.toEntity());

	    AlumnoEvent event = new AlumnoEvent();

	    event.setAlumnoId(nuevoAlumno.getId());
	    event.setCursoId(nuevoAlumno.getCursoId());

	    rabbitTemplate.convertAndSend(
	            RabbitMQConfig.EXCHANGE,
	            RabbitMQConfig.ROUTING_KEY,
	            event
	    );
	}
	
	 @Override
	    public AlumnoConCursoDto obtenerConCurso(Long alumnoId) {
	        Alumno alumno = alumnorepository.findById(alumnoId)
	                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con id: " + alumnoId));

	        AlumnoConCursoDto dto = new AlumnoConCursoDto();
	        dto.setId(alumno.getId());
	        dto.setNombre(alumno.getNombre());
	        dto.setApellido(alumno.getApellido());
	        dto.setGmail(alumno.getGmail());

	        return dto;
	    }
}