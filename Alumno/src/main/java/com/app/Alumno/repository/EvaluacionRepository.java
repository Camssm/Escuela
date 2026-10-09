package com.app.Alumno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Alumno.entity.Evaluacion;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

    List<Evaluacion> findByCursoId(Long cursoId);

}