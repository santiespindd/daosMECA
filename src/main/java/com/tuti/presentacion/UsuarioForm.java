package com.tuti.presentacion;

import java.util.Date;

import com.tuti.entidades.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Objeto necesario para insertar o actualizar un usuario
 */
public class UsuarioForm {

	@NotNull(message = "El DNI no puede ser nulo")
	@Min(7000000)
	private Long dni;
	@NotNull
	@Size(min = 2, max = 30, message = "El apellido debe contener entre {min} y {max} caracteres")
	private String apellido;
	@NotNull
	@Size(min = 2, max = 30, message = "El nombre debe contener entre {min} y {max} caracteres")
	private String nombre;
	private String domicilio;
	@Email(message = "El e-mail ingresado no es válido")
	private String email;
	private Date fechaNac;
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).+$", message = "La patente debe contener letras y números")
	private String patente;
	@NotEmpty(message = "Se debe proporcionar una contraseña")
	private String password;

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

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario toPojo() {
		Usuario u = new Usuario();
		u.setDni(this.getDni());
		u.setApellido(this.getApellido());
		u.setNombre(this.getNombre());
		u.setDomicilio(this.getDomicilio());
		u.setEmail(this.getEmail());
		u.setFechaNac(this.getFechaNac());
		u.setPatente(this.getPatente().toUpperCase());
		u.setPassword(this.getPassword());
		return u;
	}

}
