package com.app.Cursos.dto;

import com.app.Cursos.entity.Cursos;
import com.app.Cursos.mappers.IMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursosEditarDto implements IMapper<Cursos> {

    private Long id;
    private String materia;
    private String nombreMaestro;
    private Long docenteId;
    private String horario;
    private int cupo;

    @Override
    public Cursos mapperTo() {

        Cursos curso = new Cursos(
            id,
            materia,
            nombreMaestro,
            horario,
            cupo
        );

        curso.setDocenteId(docenteId);

        return curso;
    }
}