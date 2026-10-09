package com.app.Alumno.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Alumno.dto.EvaluacionDto;
import com.app.Alumno.service.EvaluacionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    @PostMapping
    public ResponseEntity<EvaluacionDto> crear(@RequestBody EvaluacionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionService.crear(dto));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<EvaluacionDto>> listarPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(evaluacionService.listarPorCurso(cursoId));
    }
}
