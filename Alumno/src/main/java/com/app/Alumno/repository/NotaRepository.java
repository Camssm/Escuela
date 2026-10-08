package com.app.Alumno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Alumno.entity.Nota;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByEvaluacionId(Long evaluacionId);
    List<Nota> findByAlumnoId(Long alumnoId);
}
