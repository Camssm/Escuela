package com.app.Alumno.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.Alumno.entity.Administracion;

public interface AdministracionRepository extends JpaRepository<Administracion, Long> {

    Optional<Administracion> findByEmailAndCargo(String email, String cargo);
    
    boolean existsByIdAndCargo(Long id, String cargo);

}