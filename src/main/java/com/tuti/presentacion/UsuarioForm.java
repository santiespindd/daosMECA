package com.tuti.presentacion;

import com.tuti.entidades.Usuario;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Objeto necesario para insertar o eliminar una persona. 
 * Nótese que en lugar de referenciar al objeto Ciudad, reemplaza ese atributo por el idCiudad, lo cual resulta mas sencillo de representar en JSON
 *
 */
public class UsuarioForm {


	@NotNull(message = "el dni no puede ser nulo")
	@Min(7000000)
	private Long dni;
	@NotNull
	@Size(min=2, max=30, message = "apellido demasiado largo")
	private String apellido;
	@NotNull
	@Size(min=2, max=30)
	private String nombre;
	@NotNull
	private Long idCiudad;
	
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
	public Long getIdCiudad() {
		return idCiudad;
	}
	public void setIdCiudad(Long idCiudad) {
		this.idCiudad = idCiudad;
	}
	
	public Usuario toPojo()
	{
		Usuario u = new Usuario();
		u.setDni(this.getDni());
		u.setApellido(this.getApellido());
		u.setNombre(this.getNombre());
		u.setDni(this.getDni());
		return u;
	}
	
}
