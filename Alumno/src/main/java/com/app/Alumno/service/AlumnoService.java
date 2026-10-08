package com.app.Alumno.service;

import java.util.List;

import com.app.Alumno.dto.AlumnoConCursoDto;
import com.app.Alumno.mappers.AlumnoMapper;

public interface AlumnoService {
	
    List<AlumnoMapper> listar() throws Exception;
    
    void agregar(AlumnoMapper alumnoMapper) throws Exception;

    AlumnoConCursoDto obtenerConCurso(Long alumnoId);

}