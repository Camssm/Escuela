package com.app.Administracion.service;

import java.util.List;
import com.app.Administracion.mappers.AdministracionMapper;

public interface AdministracionService {

    void agregar(AdministracionMapper administracionMapper) throws Exception;

    List<AdministracionMapper> listar() throws Exception;
}
