package com.app.Cursos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "alumnos")
@Data
public class Alumno {

	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long id;
	
	   @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "cursos_id") 
	   private Cursos cursos;
	
	 
	   
	
}