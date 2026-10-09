package com.app.Cursos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alumnos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alumno {

	   @Id
	   private Long id;
	
	   @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "curso_id") 
	   private Cursos curso;
	
	 
	   public Alumno (long id) {
		   this.id = id;
	   }
	   
	
}