package com.app.Alumno.service;

import java.util.List;

import com.app.Alumno.dto.NotaAgregarDto;
import com.app.Alumno.dto.NotaDto;
import com.app.Alumno.dto.NotaEditarDto;

public interface NotaService {

    NotaDto crear(NotaAgregarDto dto);

    List<NotaDto> listarPorEvaluacion(Long evaluacionId);

    NotaDto editar(Long id, NotaEditarDto dto);
    
    void eliminar(Long id);
}
