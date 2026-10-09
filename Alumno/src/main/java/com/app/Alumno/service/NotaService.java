
package com.app.Alumno.service;

import java.util.List;

import com.app.Alumno.dto.NotaAgregarDto;
import com.app.Alumno.dto.NotaDto;

public interface NotaService {

    NotaDto crear(NotaAgregarDto dto);

    List<NotaDto> listarPorEvaluacion(Long evaluacionId);

    NotaDto modificar(Long id, NotaDto dto);

    void eliminar(Long id);
}
