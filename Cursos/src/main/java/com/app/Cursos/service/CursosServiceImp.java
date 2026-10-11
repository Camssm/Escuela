package com.app.Cursos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.app.Cursos.dto.AlumnoDto;
import com.app.Cursos.dto.CursosConAlumnoDto;
import com.app.Cursos.dto.CursosDto;
import com.app.Cursos.entity.Cursos;
import com.app.Cursos.exception.BusinessException;
import com.app.Cursos.exception.RequestException;
import com.app.Cursos.mappers.IMapper;
import com.app.Cursos.repository.CursosRepository;
import com.app.Cursos.repository.DocenteRepository;

@Service
public class CursosServiceImp implements CursosService {

	@Autowired
	CursosRepository cursosRepository;

	@Autowired
	DocenteRepository docenteRepository;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public void agregar(IMapper<Cursos> mapper) throws Exception {

		Cursos curso = mapper.mapperTo();

		validarDocente(curso.getDocenteId());

		cursosRepository.save(curso);
	}

	@Override
	public List<CursosDto> listar() throws Exception {

		List<Cursos> registros = cursosRepository.findAll();

		List<CursosDto> dtos = new ArrayList<>();

		for (Cursos curso : registros) {

			CursosDto dto = new CursosDto();

			dto.setId(curso.getId());
			dto.setMateria(curso.getMateria());
			dto.setDocenteId(curso.getDocenteId());
			dto.setHorario(curso.getHorario());
			dto.setCupo(curso.getCupo());

			if (curso.getDocenteId() != null) {
				docenteRepository.findById(curso.getDocenteId()).ifPresent(d ->
						dto.setNombreMaestro(d.getNombre() + " " + d.getApellido()));
			}

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public CursosDto obtenerPorId(Long id) {

		Cursos curso = cursosRepository.findById(id).orElseThrow(
				() -> new BusinessException("P-404", HttpStatus.NOT_FOUND, "Curso no encontrado con id: " + id));

		CursosDto dto = new CursosDto();

		dto.setId(curso.getId());
		dto.setDocenteId(curso.getDocenteId());
		dto.setMateria(curso.getMateria());
		dto.setHorario(curso.getHorario());
		dto.setCupo(curso.getCupo());

		if (curso.getDocenteId() != null) {
			docenteRepository.findById(curso.getDocenteId()).ifPresent(d ->
					dto.setNombreMaestro(d.getNombre() + " " + d.getApellido()));
		}

		return dto;
	}

	@Override
	public void editar(IMapper<Cursos> mapper) throws Exception {

		Cursos datosNuevos = mapper.mapperTo();

		if (datosNuevos.getId() == null) {
			throw new RequestException("P-400", "El id del curso es obligatorio");
		}

		Cursos existente = cursosRepository.findById(datosNuevos.getId())
				.orElseThrow(() -> new BusinessException(
						"P-404",
						HttpStatus.NOT_FOUND,
						"Curso no encontrado con id: " + datosNuevos.getId()));

		validarDocente(datosNuevos.getDocenteId());

		existente.setMateria(datosNuevos.getMateria());
		existente.setDocenteId(datosNuevos.getDocenteId());
		existente.setHorario(datosNuevos.getHorario());
		existente.setCupo(datosNuevos.getCupo());

		cursosRepository.save(existente);
	}

	@Override
	public void eliminar(Long id) throws Exception {

		Cursos curso = cursosRepository.findById(id).orElseThrow(
				() -> new BusinessException("P-404", HttpStatus.NOT_FOUND, "Curso no encontrado con id: " + id));

		if (!curso.getAlumnos().isEmpty()) {
			throw new BusinessException("P-409", HttpStatus.CONFLICT,
					"No se puede eliminar el curso porque tiene alumnos asignados");
		}

		cursosRepository.delete(curso);
	}

	@Override
	public CursosConAlumnoDto obtenerConAlumnos(Long id) {

		Cursos curso = cursosRepository.findById(id).orElseThrow(
				() -> new BusinessException("P-404", HttpStatus.NOT_FOUND, "Curso no encontrado con id: " + id));

		CursosConAlumnoDto dto = new CursosConAlumnoDto();

		dto.setId(curso.getId());
		dto.setMateria(curso.getMateria());
		dto.setDocenteId(curso.getDocenteId());
		dto.setHorario(curso.getHorario());
		dto.setCupo(curso.getCupo());

		if (curso.getDocenteId() != null) {
		    docenteRepository.findById(curso.getDocenteId()).ifPresent(d ->
		            dto.setNombreMaestro(d.getNombre() + " " + d.getApellido()));
		}
		
		try {
			List<AlumnoDto> alumnos = restTemplate
					.exchange("http://MS-ALUMNOS/api/alumnos/curso/" + id,
							HttpMethod.GET,
							null,
							new ParameterizedTypeReference<List<AlumnoDto>>() {
							})
					.getBody();

			dto.setAlumnos(alumnos != null ? alumnos : new ArrayList<>());

		} catch (HttpClientErrorException.NotFound e) {
			throw new BusinessException("P-404", HttpStatus.NOT_FOUND,
					"No se encontraron los alumnos del curso con id: " + id);
		} catch (RestClientException e) {
			throw new BusinessException("P-503", HttpStatus.SERVICE_UNAVAILABLE,
					"No se pudieron consultar los alumnos del curso, intentá más tarde");
		}

		return dto;
	}

	private void validarDocente(Long docenteId) {

		if (docenteId == null || docenteId <= 0) {
			throw new RequestException("P-400", "El ID del docente es obligatorio");
		}

		Boolean esDocente;

		try {
			esDocente = restTemplate.getForObject(
					"http://MS-ADMINISTRACION/api/administracion/docente/" + docenteId,
					Boolean.class);

		} catch (HttpClientErrorException.NotFound e) {
			throw new RequestException("P-400", "El ID ingresado no corresponde a un docente");
		} catch (RestClientException e) {
			throw new BusinessException("P-503", HttpStatus.SERVICE_UNAVAILABLE,
					"No se pudo validar el docente, intentá más tarde");
		}

		if (!Boolean.TRUE.equals(esDocente)) {
			throw new RequestException("P-400", "El ID ingresado no corresponde a un docente");
		}
	}
	
	@Override
	public boolean docenteTieneCursos(Long docenteId) {
	    return cursosRepository.existsByDocenteId(docenteId);
	}
}