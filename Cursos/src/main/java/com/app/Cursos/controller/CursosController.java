package com.app.Cursos.controller;

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

import com.app.Cursos.dto.CursosDto;
import com.app.Cursos.mappers.CursosMapper;
import com.app.Cursos.service.CursosService;

@RestController
@RequestMapping(value = "/api/cursos")
public class CursosController {

    @Autowired
    private CursosService cursosService;

    @RequestMapping(value = "/cursos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CursosDto>> listar() throws Exception {
        List<CursosMapper> mappers = cursosService.listar();
        List<CursosDto> dtos = new ArrayList<CursosDto>();
        for (CursosMapper mapper : mappers) {
            dtos.add(mapper.toDto());
        }
        return ResponseEntity.ok(dtos);
    }

    @RequestMapping(value = "/agregar", method = RequestMethod.PUT)
    public ResponseEntity<Void> agregar(@RequestBody CursosDto cursosDto) throws Exception {
        cursosService.agregar(cursosDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CursosDto> obtenerPorId(@PathVariable Long id) {
       return ResponseEntity.ok().body(cursosService.obtenerPorId(id));
       
    }
}