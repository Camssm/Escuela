package com.app.Cursos.entity;

import java.util.ArrayList;
import java.util.List;

import com.app.Cursos.dto.CursosDto;
import com.app.Cursos.mappers.CursosMapper;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="cursos")
public class Cursos implements CursosMapper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String materia;
	private String nombreMaestro;
	private int numSalon;
	
	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Alumno> alumnos = new ArrayList<>();
	
	public Cursos() {
		super();
	}

	public Cursos(Long id, String materia, String nombreMaestro, int numSalon) {
		super();
		this.id = id;
		this.materia = materia;
		this.nombreMaestro = nombreMaestro;
		this.numSalon = numSalon;
	}

	public Cursos(String materia, String nombreMaestro, int numSalon) {
		super();
		this.materia = materia;
		this.nombreMaestro = nombreMaestro;
		this.numSalon = numSalon;

	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getNombreMaestro() {
		return nombreMaestro;
	}

	public void setNombreMaestro(String nombreMaestro) {
		this.nombreMaestro = nombreMaestro;
	}

	public int getNumSalon() {
		return numSalon;
	}

	public void setNumSalon(int numSalon) {
		this.numSalon = numSalon;
	}

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public CursosDto toDto() {
        return new CursosDto(id, materia, nombreMaestro, numSalon, new ArrayList<>());
	}

    public Cursos toEntity() {
        return this;
    }
    
    public void addAlumno(Alumno alumno) {
        alumnos.add(alumno);
        alumno.setCurso(this);
    }
}