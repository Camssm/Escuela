package com.app.Alumno.entity;

import com.app.Alumno.Enum.Estado;
import com.app.Alumno.dto.AlumnoDto;
import com.app.Alumno.mappers.AlumnoMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="alumnos")
public class Alumno implements AlumnoMapper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String apellido;
	private String gmail;
    private Estado estado;

	
	@Column(name = "curso_id")
	private Long cursoId;
	
	public Alumno() {
		super();
	}
	
	public Alumno(Long id, String nombre, String apellido, String gmail, Estado estado, Long cursoId) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.gmail = gmail;
		this.estado = estado;
		this.cursoId = cursoId;
	}

	public Alumno(String nombre, String apellido, String gmail, Estado estado, Long cursoId) {
	    super();
	    this.nombre = nombre;
	    this.apellido = apellido;
	    this.gmail = gmail;
		this.estado = estado;
	    this.cursoId = cursoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}
	
	public AlumnoDto toDto() {
	    return new AlumnoDto(id, nombre, apellido, gmail, estado, cursoId);
	}

    public Alumno toEntity() {
        return this;
    }
}