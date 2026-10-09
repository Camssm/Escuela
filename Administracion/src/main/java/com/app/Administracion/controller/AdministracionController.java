package com.app.Administracion.controller;

import java.util.List;

import com.app.Administracion.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.Administracion.dto.AdministracionDto;
import com.app.Administracion.dto.AdministracionAltaDto;
import com.app.Administracion.dto.AdministracionEditarDto;
import com.app.Administracion.service.AdministracionService;

@RestController
@RequestMapping("/api/administracion")
public class AdministracionController {

    @Autowired
    private AdministracionService administracionService;

    @RequestMapping(value = "/Administracion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdministracionDto>> listar() throws Exception {
        List<AdministracionDto> administraciones = administracionService.listar();
        return ResponseEntity.ok(administraciones);
    }

    @RequestMapping(value = "/agregar", method = RequestMethod.PUT)
    public ResponseEntity<?> agregar(
            @RequestBody AdministracionAltaDto administracionDto) throws Exception {

        administracionService.agregar(administracionDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/buscar", method = RequestMethod.GET)
    public ResponseEntity<AdministracionDto> buscar(@RequestParam int id) throws Exception {
        AdministracionDto administracion = administracionService.obtenerPorId(id);

        if (administracion == null) {
            throw new BusinessException("P-404", HttpStatus.NOT_FOUND,
                    "Administrativo no encontrado con id: " + id
            );
        }

        return ResponseEntity.ok(administracion);
    }

    @RequestMapping(value = "/editar", method = RequestMethod.PUT)
    public ResponseEntity<?> editar(
            @RequestBody AdministracionEditarDto administracionDto) throws Exception {

        administracionService.editar(administracionDto);
        return ResponseEntity.ok().build();
    }
}
