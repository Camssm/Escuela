package com.app.Cursos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Cursos.dto.CursosDto;
import com.app.Cursos.entity.Cursos;
import com.app.Cursos.mappers.CursosMapper;
import com.app.Cursos.repository.CursosRepository;

@Service
public class CursosServiceImp implements CursosService {

	@Autowired
	CursosRepository cursosRepository;
	
	@Override
    public List<CursosMapper> listar() throws Exception {
		List<Cursos> registros = cursosRepository.findAll();
        List<CursosMapper> mappers = new ArrayList<>();
        for (Cursos registro : registros) {
            mappers.add(registro);
        }
        return mappers;
    }

	@Override
	public void agregar(CursosMapper cursosMapper) throws Exception {
		cursosRepository.save(cursosMapper.toEntity());
	}
	
	@Override
	public CursosDto obtenerPorId(Long id) {

	    Optional<Cursos> cursoOpt = cursosRepository.findById(id);

	    if (cursoOpt.isEmpty()) {
	        throw new RuntimeException("Curso no encontrado con id: " + id);
	    }

	    Cursos curso = cursoOpt.get();

	    CursosDto cursosDto = new CursosDto();

	    cursosDto.setId(curso.getId());
	    cursosDto.setMateria(curso.getMateria());
	    cursosDto.setNombreMaestro(curso.getNombreMaestro());
	    cursosDto.setNumSalon(curso.getNumSalon());

	    curso.getAlumnos().forEach(alumno -> {
	        cursosDto.getAlumnos().add(alumno.getId());
	    });

	    return cursosDto;
	}
}