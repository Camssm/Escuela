package com.app.Administracion.service;

import java.util.List;
import java.util.Optional;

import com.app.Administracion.entity.Administracion;
import com.app.Administracion.mappers.IMapper;

public interface AdministracionService {

    void agregar(IMapper<Administracion> mapper) throws Exception;

    List<Administracion> listar() throws Exception;

    Optional<Administracion> obtenerPorId(int id) throws Exception;

    void editar(IMapper<Administracion> mapper) throws Exception;
}