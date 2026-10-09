package com.app.Cursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Cursos.dto.CursosAgregarDto;
import com.app.Cursos.dto.CursosDto;
import com.app.Cursos.dto.CursosEditarDto;
import com.app.Cursos.service.CursosService;

@RestController
@RequestMapping(value = "/api/cursos")
public class CursosController {

    @Autowired
    private CursosService cursosService;

    @RequestMapping(
        value = "/cursos",
        method = RequestMethod.GET
    )
    public ResponseEntity<List<CursosDto>> listar() throws Exception {

        return ResponseEntity.ok(
            cursosService.listar()
        );
    }

    @RequestMapping(
        value = "/agregar",
        method = RequestMethod.PUT
    )
    public ResponseEntity<Void> agregar(
            @RequestBody CursosAgregarDto cursosDto) throws Exception {

        cursosService.agregar(cursosDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursosDto> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            cursosService.obtenerPorId(id)
        );
    }

    @RequestMapping(
        value = "/editar",
        method = RequestMethod.PUT
    )
    public ResponseEntity<Void> editar(
            @RequestBody CursosEditarDto cursosDto) throws Exception {

        cursosService.editar(cursosDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) throws Exception {

        cursosService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}