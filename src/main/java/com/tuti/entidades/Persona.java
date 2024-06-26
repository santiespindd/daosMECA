package com.tuti.entidades;

import java.util.Collection;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Entity
public class Persona {


	@Id
	private Long dni;
	@NotNull
	@Size(min = 1,max = 100, message = "Debe completar el apellido")
	private String apellido;
	
	private String nombre;
	

	@Email(message = "El e-mail ingresado no es valido")
	private String email;
	
	@ElementCollection
	private Collection<String> notas;
	
	@ManyToOne
	private Ciudad ciudad;
	
	
	
	public Long getDni() {
		return dni;
	}
	public void setDni(Long dni) {
		this.dni = dni;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	@Override
	public String toString() {
		return dni+" - "+ nombre +" "+ apellido;
	}
	
	
	
	public Ciudad getCiudad() {
		return ciudad;
	}
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Collection<String> getNotas() {
		return notas;
	}
	public void setNotas(Collection<String> notas) {
		this.notas = notas;
	}
	
	
	
}
