package com.app.Cursos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "docentes")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Docente {
    @Id
    private Long id;   
    private String nombre;
    private String apellido;
    private String email;
}
