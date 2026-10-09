package com.app.Alumno.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.app.Alumno.Enum.TipoEvaluacion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evaluaciones")
@Getter 
@Setter 
@NoArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoEvaluacion tipo;

    private String descripcion;
    private LocalDate fecha;

    @Column(name = "curso_id")
    private Long cursoId;

    @Column(name = "docente_id")
    private Long docenteId;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL)
    private List<Nota> notas = new ArrayList<>();
}
