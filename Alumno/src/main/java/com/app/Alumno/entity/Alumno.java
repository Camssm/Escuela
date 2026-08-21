package com.app.Alumno.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Alumno {

	@Id
	private int id;
	private String nombre;
	private String apellido;
	private String gmail;
}