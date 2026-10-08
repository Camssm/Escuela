<<<<<<< HEAD

=======
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import com.app.Alumno.dto.NotaAgregarDto;
=======
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
import com.app.Alumno.dto.NotaDto;
import com.app.Alumno.service.NotaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notas")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;

    @PostMapping
<<<<<<< HEAD
    public ResponseEntity<NotaDto> crear(
            @RequestBody NotaAgregarDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notaService.crear(dto));
    }

    @GetMapping("/evaluacion/{evaluacionId}")
    public ResponseEntity<List<NotaDto>> listar(
            @PathVariable Long evaluacionId) {
        return ResponseEntity.ok(
                notaService.listarPorEvaluacion(evaluacionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaDto> modificar(
            @PathVariable Long id,
            @RequestBody NotaDto dto) {
        return ResponseEntity.ok(
                notaService.modificar(id, dto));
=======
    public ResponseEntity<NotaDto> crear(@RequestBody NotaDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notaService.crear(dto));
    }

    @GetMapping("/evaluacion/{evaluacionId}")
    public ResponseEntity<List<NotaDto>> listar(@PathVariable Long evaluacionId) {
        return ResponseEntity.ok(notaService.listarPorEvaluacion(evaluacionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaDto> modificar(@PathVariable Long id, @RequestBody NotaDto dto) {
        return ResponseEntity.ok(notaService.modificar(id, dto));
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
