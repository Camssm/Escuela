package com.app.Administracion.service;

import java.util.List;

import com.app.Administracion.dto.AdministracionDto;
import com.app.Administracion.entity.Administracion;
import com.app.Administracion.mappers.IMapper;

public interface AdministracionService {

    void agregar(IMapper<Administracion> mapper) throws Exception;

    List<AdministracionDto> listar() throws Exception;

    AdministracionDto obtenerPorId(int id) throws Exception;

    void editar(IMapper<Administracion> mapper) throws Exception;
}