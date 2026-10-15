package com.app.Alumno.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Alumno.dto.NotaAgregarDto;
import com.app.Alumno.dto.NotaDto;
import com.app.Alumno.dto.NotaEditarDto;
import com.app.Alumno.service.NotaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notas")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;

    @PostMapping
    public ResponseEntity<NotaDto> crear(
            @RequestHeader("X-User-Username") String username,
            @RequestBody NotaAgregarDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notaService.crear(dto, username));
    }

    @GetMapping("/evaluacion/{evaluacionId}")
    public ResponseEntity<List<NotaDto>> listar(
            @PathVariable Long evaluacionId) {
        return ResponseEntity.ok(
                notaService.listarPorEvaluacion(evaluacionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaDto> editar(
            @RequestHeader("X-User-Username") String username,
            @PathVariable Long id,
            @RequestBody NotaEditarDto dto) {
        return ResponseEntity.ok(
                notaService.editar(id, dto, username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @RequestHeader("X-User-Username") String username,
            @PathVariable Long id) {
        notaService.eliminar(id, username);
        return ResponseEntity.noContent().build();
    }
}