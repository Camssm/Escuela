package com.app.Cursos.dto;

import com.app.Cursos.entity.Cursos;
import com.app.Cursos.mappers.IMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursosAgregarDto implements IMapper<Cursos> {

    private String materia;
    private String nombreMaestro;
    private Long docenteId;
    private String horario;
    private int cupo;

    @Override
    public Cursos mapperTo() {

        Cursos curso = new Cursos(
            materia,
            nombreMaestro,
            horario,
            cupo
        );

        curso.setDocenteId(docenteId);

        return curso;
    }
}