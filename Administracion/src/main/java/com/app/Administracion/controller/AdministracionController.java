package com.app.Administracion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Administracion.dto.AdministracionDto;
import com.app.Administracion.mappers.AdministracionMapper;
import com.app.Administracion.service.AdministracionService;

@RestController
@RequestMapping("/api/administracion")
public class AdministracionController {

    @Autowired
    private AdministracionService administracionService;

    @RequestMapping(value = "/Administracion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdministracionDto>> listar() throws Exception {
        List<AdministracionMapper> mappers = administracionService.listar();
        List<AdministracionDto> dtos = new ArrayList<AdministracionDto>();
        for (AdministracionMapper mapper : mappers) {
            dtos.add(mapper.toDto());
        }
        return ResponseEntity.ok(dtos);
    }

    @RequestMapping(value = "/agregar", method = RequestMethod.PUT)
    public ResponseEntity<?> agregar(@RequestBody AdministracionDto administracionDto) throws Exception {
        administracionService.agregar(administracionDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
