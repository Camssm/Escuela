package com.app.Cursos.service;

import java.util.List;

import com.app.Cursos.dto.CursosDto;
import com.app.Cursos.entity.Cursos;
import com.app.Cursos.mappers.IMapper;

public interface CursosService {

    void agregar(IMapper<Cursos> mapper) throws Exception;

    List<CursosDto> listar() throws Exception;

    CursosDto obtenerPorId(Long id);

    void editar(IMapper<Cursos> mapper) throws Exception;

    void eliminar(Long id) throws Exception;
}