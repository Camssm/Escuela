package com.app.Alumno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.Alumno.entity.Administracion;

public interface AdministracionRepository extends JpaRepository<Administracion, Long> {

    boolean existsByIdAndCargo(Long id, String cargo);
}