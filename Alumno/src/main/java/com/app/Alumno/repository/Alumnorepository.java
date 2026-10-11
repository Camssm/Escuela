
package com.app.Alumno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Alumno.entity.Alumno;
import com.app.Alumno.Enum.Estado;

public interface Alumnorepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findByCursoIdAndEstado(Long cursoId, Estado estado);

}
