package com.app.Cursos.listener.objects;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AlumnoEvent {

    private Long alumnoId;

    private Long cursoId;

    private String tipo;
}
