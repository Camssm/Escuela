package com.app.Administracion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Administracion.entity.Administracion;
import com.app.Administracion.mappers.AdministracionMapper;
import com.app.Administracion.repository.AdministracionRepository;

@Service
public class AdministracionServiceImp implements AdministracionService {

    @Autowired
    private AdministracionRepository administracionRepository;

    @Override
    public void agregar(AdministracionMapper administracionMapper) throws Exception {
        administracionRepository.save(administracionMapper.toEntity());
    }

    @Override
    public List<AdministracionMapper> listar() throws Exception {
        List<Administracion> registros = administracionRepository.findAll();
        List<AdministracionMapper> mappers = new ArrayList<>();
        for (Administracion registro : registros) {
            mappers.add(registro);
        }
        return mappers;
    }
}