package com.app.Alumno.dto;

import java.time.LocalDate;

import com.app.Alumno.Enum.TipoEvaluacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EvaluacionDto {
    private Long id;
    private TipoEvaluacion tipo;
    private String descripcion;
    private LocalDate fecha;
    private Long cursoId;
    private Long docenteId;
}
