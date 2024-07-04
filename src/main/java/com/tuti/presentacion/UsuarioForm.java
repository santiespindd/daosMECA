package com.tuti.presentacion;

import java.math.BigDecimal;
import java.util.Date;

import com.tuti.entidades.Usuario;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Objeto necesario para insertar o eliminar una persona.
 */
public class UsuarioForm {

	@NotNull(message = "El dni no puede ser nulo")
	@Min(7000000)
	private Long dni;
	@NotNull
	@Size(min = 2, max = 30, message = "Apellido demasiado largo")
	private String apellido;
	@NotNull
	@Size(min = 2, max = 30)
	private String nombre;
	private String domicilio;
	private String email;
	private Date fechaNac;
	private String patente;
	private String password;
	private BigDecimal saldo;

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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Usuario toPojo() {
		Usuario u = new Usuario();
		u.setDni(this.getDni());
		u.setApellido(this.getApellido());
		u.setNombre(this.getNombre());
		u.setDomicilio(this.getDomicilio());
		u.setEmail(this.getEmail());
		u.setFechaNac(this.getFechaNac());
		u.setPatente(this.getPatente());
		u.setPassword(this.getPassword());
		u.setSaldo(this.getSaldo());
		return u;
	}

}
