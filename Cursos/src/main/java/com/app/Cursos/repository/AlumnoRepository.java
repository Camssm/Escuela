package com.app.Cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.Cursos.entity.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

}
