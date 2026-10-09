package com.app.Alumno.service;

import java.util.List;

import com.app.Alumno.dto.AlumnoConCursoDto;
import com.app.Alumno.dto.AlumnoDto;
import com.app.Alumno.entity.Alumno;
import com.app.Alumno.mappers.IMapper;

public interface AlumnoService {

    void agregar(IMapper<Alumno> mapper) throws Exception;

    List<AlumnoDto> listar() throws Exception;

    AlumnoConCursoDto obtenerConCurso(Long alumnoId);

    void editar(IMapper<Alumno> mapper) throws Exception;

    void cambiarEstado(IMapper<Alumno> mapper) throws Exception;
}