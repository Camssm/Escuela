package com.app.Cursos.service;

import java.util.List;

import com.app.Cursos.dto.CursosDto;
import com.app.Cursos.mappers.CursosMapper;

public interface CursosService {

    List<CursosMapper> listar() throws Exception;
    
    void agregar(CursosMapper cursosMapper) throws Exception;
    
    CursosDto obtenerPorId(Long id);

}