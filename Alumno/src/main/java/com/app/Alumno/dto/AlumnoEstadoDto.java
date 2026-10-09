package com.app.Alumno.dto;

import com.app.Alumno.Enum.Estado;
import com.app.Alumno.entity.Alumno;
import com.app.Alumno.mappers.IMapper;

public class AlumnoEstadoDto implements IMapper<Alumno> {

    private Long id;
    private Estado estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public Alumno mapperTo() {
        return new Alumno(
            id,
            null,
            null,
            null,
            estado,
            null
        );
    }
}