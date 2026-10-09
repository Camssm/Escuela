package com.app.Cursos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import com.app.Cursos.exception.BusinessException;
import com.app.Cursos.exception.RequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Cursos.dto.CursosDto;
import com.app.Cursos.entity.Cursos;
import com.app.Cursos.mappers.IMapper;
import com.app.Cursos.repository.CursosRepository;

@Service
public class CursosServiceImp implements CursosService {

    @Autowired
    CursosRepository cursosRepository;

    @Override
    public void agregar(IMapper<Cursos> mapper) throws Exception {

        Cursos curso = mapper.mapperTo();

        cursosRepository.save(curso);
    }

    @Override
    public List<CursosDto> listar() throws Exception {

        List<Cursos> registros = cursosRepository.findAll();

        List<CursosDto> dtos = new ArrayList<>();

        for (Cursos curso : registros) {

            CursosDto dto = new CursosDto();

            dto.setId(curso.getId());
            dto.setMateria(curso.getMateria());
            dto.setNombreMaestro(curso.getNombreMaestro());
            dto.setDocenteId(curso.getDocenteId());
            dto.setHorario(curso.getHorario());
            dto.setCupo(curso.getCupo());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public CursosDto obtenerPorId(Long id) {

    	Cursos curso = cursosRepository.findById(id)
    	        .orElseThrow(() -> new BusinessException(
    	                "P-404", HttpStatus.NOT_FOUND,
    	                "Curso no encontrado con id: " + id));

        CursosDto dto = new CursosDto();

        dto.setId(curso.getId());
        dto.setMateria(curso.getMateria());
        dto.setNombreMaestro(curso.getNombreMaestro());
        dto.setDocenteId(curso.getDocenteId());
        dto.setHorario(curso.getHorario());
        dto.setCupo(curso.getCupo());

        return dto;
    }

    @Override
    public void editar(IMapper<Cursos> mapper) throws Exception {

    	Cursos datosNuevos = mapper.mapperTo();

    	if (datosNuevos.getId() == null) {
    	    throw new RequestException("P-400", "El id del curso es obligatorio");
    	}

    	Cursos existente = cursosRepository.findById(datosNuevos.getId())
    	        .orElseThrow(() -> new BusinessException(
    	                "P-404", HttpStatus.NOT_FOUND,
    	                "Curso no encontrado con id: " + datosNuevos.getId()));

        existente.setMateria(datosNuevos.getMateria());
        existente.setNombreMaestro(datosNuevos.getNombreMaestro());
        existente.setDocenteId(datosNuevos.getDocenteId());
        existente.setHorario(datosNuevos.getHorario());
        existente.setCupo(datosNuevos.getCupo());

        cursosRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) throws Exception {

    	Cursos curso = cursosRepository.findById(id)
    	        .orElseThrow(() -> new BusinessException(
    	                "P-404", HttpStatus.NOT_FOUND,
    	                "Curso no encontrado con id: " + id));

    	if (!curso.getAlumnos().isEmpty()) {
    	    throw new BusinessException(
    	            "P-409", HttpStatus.CONFLICT,
    	            "No se puede eliminar el curso porque tiene alumnos asignados");
    	}

        cursosRepository.delete(curso);
    }
}