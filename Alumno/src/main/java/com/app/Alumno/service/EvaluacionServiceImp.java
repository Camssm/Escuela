package com.app.Alumno.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.app.Alumno.dto.CursosDto;
import com.app.Alumno.dto.EvaluacionAgregarDto;
import com.app.Alumno.dto.EvaluacionDto;
import com.app.Alumno.entity.Evaluacion;
import com.app.Alumno.exception.BusinessException;
import com.app.Alumno.repository.EvaluacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvaluacionServiceImp implements EvaluacionService {

	private final EvaluacionRepository evaluacionRepository;
	private final RestTemplate restTemplate;

	@Override
	public EvaluacionDto crear(EvaluacionAgregarDto dto) {

		CursosDto curso;
		try {
			curso = restTemplate.getForObject("http://MS-CURSOS/api/cursos/" + dto.getCursoId(), CursosDto.class);
		} catch (HttpClientErrorException.NotFound e) {
			throw new BusinessException("P-404", HttpStatus.NOT_FOUND,
					"El curso con id " + dto.getCursoId() + " no existe");
		} catch (RestClientException e) {
			throw new BusinessException("P-503", HttpStatus.SERVICE_UNAVAILABLE,
					"No se pudo verificar el curso, intentá más tarde");
		}

		if (curso == null || curso.getDocenteId() == null) {
			throw new BusinessException("P-400", HttpStatus.BAD_REQUEST,
					"El curso todavía no tiene un docente asignado");
		}

		Evaluacion evaluacion = dto.mapperTo();

		return toDto(evaluacionRepository.save(evaluacion));
	}

	@Override
	public List<EvaluacionDto> listarPorCurso(Long cursoId) {
		return evaluacionRepository.findByCursoId(cursoId).stream().map(this::toDto).toList();
	}

	private EvaluacionDto toDto(Evaluacion e) {
		return new EvaluacionDto(e.getId(), e.getTipo(), e.getDescripcion(), e.getFecha(), e.getCursoId());
	}
}