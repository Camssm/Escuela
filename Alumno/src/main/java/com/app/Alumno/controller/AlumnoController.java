package com.app.Alumno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Alumno.dto.AlumnoAgregarDto;
import com.app.Alumno.dto.AlumnoConCursoDto;
import com.app.Alumno.dto.AlumnoDto;
import com.app.Alumno.dto.AlumnoEditarDto;
import com.app.Alumno.dto.AlumnoEstadoDto;
import com.app.Alumno.service.AlumnoService;

@RestController
@RequestMapping(value = "/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;


    @RequestMapping(
        value = "/alumnos",
        method = RequestMethod.GET
    )
    public ResponseEntity<List<AlumnoDto>> listar() throws Exception {

        return ResponseEntity.ok(alumnoService.listar());
    }


    @RequestMapping(
        value = "/agregar",
        method = RequestMethod.PUT
    )
    public ResponseEntity<?> agregar(
            @RequestBody AlumnoAgregarDto alumnoDto) throws Exception {

        alumnoService.agregar(alumnoDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/{id}/con-curso")
    public ResponseEntity<AlumnoConCursoDto> obtenerConCurso(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            alumnoService.obtenerConCurso(id)
        );
    }


    @RequestMapping(
        value = "/editar",
        method = RequestMethod.PUT
    )
    public ResponseEntity<Void> editar(
            @RequestBody AlumnoEditarDto alumnoDto) throws Exception {

        alumnoService.editar(alumnoDto);

        return ResponseEntity.ok().build();
    }


    @RequestMapping(
        value = "/cambiar-estado",
        method = RequestMethod.PUT
    )
    public ResponseEntity<Void> cambiarEstado(
            @RequestBody AlumnoEstadoDto alumnoDto) throws Exception {

        alumnoService.cambiarEstado(alumnoDto);

        return ResponseEntity.ok().build();
    }
}