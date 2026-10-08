package com.app.Administracion.entity;

import com.app.Administracion.Enum.Cargo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "administracion")
public class Administracion {

	    @Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    private String nombre;
	    private String apellido;
	    private String dni;
	    private String email;
	    
	    @Enumerated(EnumType.STRING)
	    private Cargo cargo;
	    
		public Administracion() {
			super();
		}
		
		public Administracion(int id, String nombre, String apellido, String dni, String email, Cargo cargo) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.apellido = apellido;
			this.dni = dni;
			this.email = email;
			this.cargo = cargo;
		}
		
		public Administracion(String nombre, String apellido, String dni, String email, Cargo cargo) {
		    super();
		    this.nombre = nombre;
		    this.apellido = apellido;
		    this.dni = dni;
		    this.email = email;
		    this.cargo = cargo;
		}


		public int getId() {
			return id;
		}
		public void setId(int id) {
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
		public String getDni() {
			return dni;
		}
		public void setDni(String dni) {
			this.dni = dni;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public Cargo getCargo() {
			return cargo;
		}
		public void setCargo(Cargo cargo) {
			this.cargo = cargo;
		}

}
