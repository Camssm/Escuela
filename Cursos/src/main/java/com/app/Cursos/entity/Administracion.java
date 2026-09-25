package com.app.Cursos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "administracion")
@NoArgsConstructor
public class Administracion {

	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long id;
	
	   @OneToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "administracion_id", referencedColumnName = "id") 
	   private Cursos cursos;
	
	
}