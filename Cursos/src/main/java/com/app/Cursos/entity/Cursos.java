package com.app.Cursos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Cursos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String materia;
	private String nombreMaestro;
	private int numSalon;
	
	public Cursos() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
	
	
}
