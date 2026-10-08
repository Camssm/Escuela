package com.app.Alumno.dto;

import com.app.Alumno.entity.Alumno;
import com.app.Alumno.mappers.IMapper;

public class AlumnoAgregarDto implements IMapper<Alumno> {

    private String nombre;
    private String apellido;
    private String gmail;
    private Long cursoId;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    @Override
    public Alumno mapperTo() {
        return new Alumno(
            nombre,
            apellido,
            gmail,
            null,
            cursoId
        );
    }
}