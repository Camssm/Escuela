package com.app.Cursos.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos")
public class Cursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String materia;
    private String nombreMaestro;

    @Column(name = "docente_id")
    private Long docenteId;

    private String horario;
    private int cupo;

    @OneToMany(mappedBy = "curso")
    private List<Alumno> alumnos = new ArrayList<>();

	public Cursos() {
		super();
	}

	public Cursos(Long id, String materia, String nombreMaestro, String horario, int cupo) {
		super();
		this.id = id;
		this.materia = materia;
		this.nombreMaestro = nombreMaestro;
		this.horario = horario;
		this.cupo = cupo;
	}

	public Cursos(String materia, String nombreMaestro, String horario, int cupo) {
		super();
		this.materia = materia;
		this.nombreMaestro = nombreMaestro;
		this.horario = horario;
		this.cupo = cupo;

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

	public Long getDocenteId() { 
		return docenteId; 
		}
	
	public void setDocenteId(Long docenteId) { 
		this.docenteId = docenteId; 
		}

	public String getHorario() { 
		return horario; 
		}
	
	public void setHorario(String horario) { 
		this.horario = horario; 
		}
	
	public int getCupo() {
		return cupo;
	}

	public void setCupo(int cupo) {
		this.cupo = cupo;
	}

	public List<Alumno> getAlumnos() { 
		return alumnos; 
		}
	
	public void setAlumnos(List<Alumno> alumnos) { 
		this.alumnos = alumnos; 
		}

    public void addAlumno(Alumno alumno) {
        alumnos.add(alumno);
        alumno.setCurso(this);
    }
}