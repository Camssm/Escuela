package com.app.Alumno.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Alumno.dto.AlumnoConCursoDto;
import com.app.Alumno.dto.AlumnoDto;
import com.app.Alumno.mappers.AlumnoMapper;
import com.app.Alumno.service.AlumnoService;

@RestController
@RequestMapping(value = "/api/alumnos")
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoService;

	@RequestMapping(value = "/alumnos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AlumnoDto>> listar() throws Exception {
		List<AlumnoMapper> mappers = alumnoService.listar();
		List<AlumnoDto> dtos = new ArrayList<AlumnoDto>();
		for (AlumnoMapper mapper : mappers) {
			dtos.add(mapper.toDto());
		}
		return ResponseEntity.ok(dtos);
	}

	@RequestMapping(value = "/agregar", method = RequestMethod.PUT)
	public ResponseEntity<?> agregar(@RequestBody AlumnoDto alumnoDto) throws Exception {
		alumnoService.agregar(alumnoDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/con-curso")
    public ResponseEntity<AlumnoConCursoDto> obtenerConCurso(@PathVariable Long id) {
        return ResponseEntity.ok(alumnoService.obtenerConCurso(id));
    }
}