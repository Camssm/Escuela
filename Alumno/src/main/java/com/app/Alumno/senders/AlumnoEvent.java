package com.app.Alumno.senders;

import lombok.Data;

@Data
public class AlumnoEvent {

    private Long alumnoId;

    private Long cursoId;

    private String tipo;
}
