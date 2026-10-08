package com.app.Alumno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NotaDto {
    private Long id;
    private Double valor;
    private Long evaluacionId;
    private Long alumnoId;
}
