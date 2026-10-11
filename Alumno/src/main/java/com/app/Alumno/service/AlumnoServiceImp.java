package com.app.Alumno.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import com.app.Alumno.exception.BusinessException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
	public List<AlumnoDto> listarPorCurso(Long cursoId) {

		try {
			restTemplate.getForObject("http://MS-CURSOS/api/cursos/" + cursoId, CursosDto.class);
		} catch (HttpClientErrorException.NotFound e) {
			throw new BusinessException("P-404", HttpStatus.NOT_FOUND, "No existe el curso con id: " + cursoId);
		} catch (RestClientException e) {
			throw new RuntimeException("No se pudo verificar el curso, intentá más tarde");
		}

		List<Alumno> registros = alumnorepository.findByCursoIdAndEstado(cursoId, Estado.ASIGNADO);

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
			try {
				restTemplate.getForObject("http://MS-CURSOS/api/cursos/" + alumno.getCursoId(), CursosDto.class);
			} catch (HttpClientErrorException.NotFound e) {
				throw new RuntimeException("El curso con id " + alumno.getCursoId() + " no existe");
			} catch (RestClientException e) {
				throw new RuntimeException("No se pudo verificar el curso, intentá más tarde");
			}

			alumno.setEstado(Estado.ASIGNADO);
		}

		Alumno nuevoAlumno = alumnorepository.save(alumno);

		if (nuevoAlumno.getCursoId() != null) {

			AlumnoEvent event = new AlumnoEvent();

			event.setAlumnoId(nuevoAlumno.getId());
			event.setCursoId(nuevoAlumno.getCursoId());
			event.setTipo("ALTA");

			rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, event);
		}
	}

	@Override
	public AlumnoConCursoDto obtenerConCurso(Long alumnoId) {

		Alumno alumno = alumnorepository.findById(alumnoId).orElseThrow(
				() -> new BusinessException("P-404", HttpStatus.NOT_FOUND, "Alumno no encontrado con id: " + alumnoId));

		AlumnoConCursoDto dto = new AlumnoConCursoDto();

		dto.setId(alumno.getId());
		dto.setNombre(alumno.getNombre());
		dto.setApellido(alumno.getApellido());
		dto.setGmail(alumno.getGmail());
		dto.setEstado(alumno.getEstado());

		if (alumno.getCursoId() != null) {

			try {

				CursosDto curso = restTemplate.getForObject("http://MS-CURSOS/api/cursos/" + alumno.getCursoId(),
						CursosDto.class);

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
				.orElseThrow(() -> new RuntimeException("Alumno no encontrado con id: " + datosNuevos.getId()));

		Long cursoAnterior = existente.getCursoId();

		Long cursoNuevo = datosNuevos.getCursoId();

		if (cursoNuevo != null && cursoNuevo <= 0) {
			cursoNuevo = null;
		}

		if (cursoNuevo != null) {
			try {
				restTemplate.getForObject("http://MS-CURSOS/api/cursos/" + cursoNuevo, CursosDto.class);
			} catch (HttpClientErrorException.NotFound e) {
				throw new RuntimeException("El curso con id " + cursoNuevo + " no existe");
			} catch (RestClientException e) {
				throw new RuntimeException("No se pudo verificar el curso, intentá más tarde");
			}
		}

		existente.setNombre(datosNuevos.getNombre());
		existente.setApellido(datosNuevos.getApellido());
		existente.setGmail(datosNuevos.getGmail());
		existente.setCursoId(cursoNuevo);

		boolean habilitado = existente.getEstado() != Estado.DESHABILITADO;

		if (habilitado) {
			existente.setEstado(cursoNuevo == null ? Estado.SINASIGNACION : Estado.ASIGNADO);
		}

		alumnorepository.save(existente);

		if (habilitado && cursoAnterior != null && !cursoAnterior.equals(cursoNuevo)) {

			AlumnoEvent baja = new AlumnoEvent();

			baja.setAlumnoId(existente.getId());
			baja.setCursoId(cursoAnterior);
			baja.setTipo("BAJA");

			rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, baja);
		}

		if (habilitado && cursoNuevo != null && !cursoNuevo.equals(cursoAnterior)) {

			AlumnoEvent alta = new AlumnoEvent();

			alta.setAlumnoId(existente.getId());
			alta.setCursoId(cursoNuevo);
			alta.setTipo("ALTA");

			rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, alta);
		}
	}

	@Override
	public void cambiarEstado(IMapper<Alumno> mapper) throws Exception {

		Alumno datos = mapper.mapperTo();

		Alumno existente = alumnorepository.findById(datos.getId()).orElseThrow(() -> new BusinessException("P-404",
				HttpStatus.NOT_FOUND, "Alumno no encontrado con id: " + datos.getId()));

		if (datos.getEstado() == null) {
			throw new BusinessException("P-400", HttpStatus.BAD_REQUEST, "El estado es obligatorio");
		}

		boolean estabaHabilitado = existente.getEstado() != Estado.DESHABILITADO;
		Long cursoActual = existente.getCursoId();

		if (datos.getEstado() == Estado.DESHABILITADO) {

			existente.setEstado(Estado.DESHABILITADO);
			alumnorepository.save(existente);

			if (cursoActual != null && estabaHabilitado) {
				AlumnoEvent baja = new AlumnoEvent();
				baja.setAlumnoId(existente.getId());
				baja.setCursoId(cursoActual);
				baja.setTipo("BAJA");

				rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, baja);
			}

		} else if (datos.getEstado() == Estado.SINASIGNACION) {

			existente.setCursoId(null);
			existente.setEstado(Estado.SINASIGNACION);
			alumnorepository.save(existente);

			if (cursoActual != null && estabaHabilitado) {
				AlumnoEvent baja = new AlumnoEvent();
				baja.setAlumnoId(existente.getId());
				baja.setCursoId(cursoActual);
				baja.setTipo("BAJA");

				rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, baja);
			}

		} else if (datos.getEstado() == Estado.ASIGNADO) {

			if (cursoActual == null) {
				throw new BusinessException("P-400", HttpStatus.BAD_REQUEST,
						"El alumno no tiene curso. Para asignarlo usá editar y elegí un curso");
			}

			existente.setEstado(Estado.ASIGNADO);
			alumnorepository.save(existente);

			if (!estabaHabilitado) {
				AlumnoEvent alta = new AlumnoEvent();
				alta.setAlumnoId(existente.getId());
				alta.setCursoId(cursoActual);
				alta.setTipo("ALTA");

				rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, alta);
			}
		}
	}

}