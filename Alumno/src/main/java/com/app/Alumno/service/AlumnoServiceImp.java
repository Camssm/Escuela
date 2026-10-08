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
import com.app.Alumno.dto.CursosDto;
import com.app.Alumno.entity.Alumno;
import com.app.Alumno.mappers.AlumnoMapper;
import com.app.Alumno.repository.Alumnorepository;
import com.app.Alumno.senders.AlumnoEvent;
import org.springframework.http.HttpStatus;
import com.app.Alumno.exception.BusinessException;
import com.app.Alumno.exception.RequestException;

@Service
public class AlumnoServiceImp implements AlumnoService {
	
	@Autowired
	Alumnorepository alumnorepository;

	@Autowired
	RestTemplate restTemplate;
	
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

	    Alumno alumno = alumnoMapper.toEntity();

	    if (alumno.getEstado() == null) {

	        if (alumno.getCursoId() == null) {
	            alumno.setEstado(Estado.SINASIGNACION);
	        } else {
	            alumno.setEstado(Estado.ASIGNADO);
	        }
	    }

	    Alumno nuevoAlumno = alumnorepository.save(alumno);

	    if (nuevoAlumno.getCursoId() != null) {

	        AlumnoEvent event = new AlumnoEvent();

	        event.setAlumnoId(nuevoAlumno.getId());
	        event.setCursoId(nuevoAlumno.getCursoId());

	        rabbitTemplate.convertAndSend(
	                RabbitMQConfig.EXCHANGE,
	                RabbitMQConfig.ROUTING_KEY,
	                event);
	    }
	}
	
	@Override
	public AlumnoConCursoDto obtenerConCurso(Long alumnoId) {
		Alumno alumno = alumnorepository.findById(alumnoId)
		        .orElseThrow(() -> new BusinessException("P-404", HttpStatus.NOT_FOUND,
		                "Alumno no encontrado con id: " + alumnoId));

	    AlumnoConCursoDto dto = new AlumnoConCursoDto();
	    dto.setId(alumno.getId());
	    dto.setNombre(alumno.getNombre());
	    dto.setApellido(alumno.getApellido());
	    dto.setGmail(alumno.getGmail());
	    dto.setEstado(alumno.getEstado());

	    if (alumno.getCursoId() != null) {
	        try {
	            CursosDto curso = restTemplate.getForObject(
	                    "http://MS-CURSOS/api/cursos/" + alumno.getCursoId(),
	                    CursosDto.class);
	            dto.setCurso(curso);
	        } catch (RestClientException e) {
	        }
	    }
	    return dto;
	}
	 
	 @Override
	 public void editar(AlumnoMapper alumnoMapper) throws Exception {
		 Alumno datosNuevos = alumnoMapper.toEntity();

		 if (datosNuevos.getId() == null) {
		     throw new RequestException("P-400", "El id del alumno es obligatorio");
		 }

		 
		 
		 Alumno existente = alumnorepository.findById(datosNuevos.getId())
		         .orElseThrow(() -> new BusinessException("P-404", HttpStatus.NOT_FOUND,
		                 "Alumno no encontrado con id: " + datosNuevos.getId()));
		 
	     existente.setNombre(datosNuevos.getNombre());
	     existente.setApellido(datosNuevos.getApellido());
	     existente.setGmail(datosNuevos.getGmail());
	     existente.setCursoId(datosNuevos.getCursoId());

	     alumnorepository.save(existente);
	 }

	 @Override
	 public void cambiarEstado(AlumnoMapper alumnoMapper) throws Exception {
		 Alumno datos = alumnoMapper.toEntity();

		 if (datos.getId() == null) {
		     throw new RequestException("P-400", "El id del alumno es obligatorio");
		 }

		 Alumno existente = alumnorepository.findById(datos.getId())
		         .orElseThrow(() -> new BusinessException("P-404", HttpStatus.NOT_FOUND,
		                 "Alumno no encontrado con id: " + datos.getId()));
		 
	     existente.setEstado(datos.getEstado());
	     alumnorepository.save(existente);
	 }
}