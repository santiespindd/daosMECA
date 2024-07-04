package com.tuti.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import com.tuti.entidades.Usuario;

/**
 * Objeto utilizado para construir la respuesta de los servicios
 * 
 *
 */
public class UsuarioResponseDTO extends RepresentationModel<UsuarioResponseDTO> {

	private Long dni;
	private String apellido;
	private String nombre;
	private String patente;
	private BigDecimal saldo;
	private String password;

	public UsuarioResponseDTO(Usuario pojo) {
		super();
		this.apellido = pojo.getApellido();
		this.nombre = pojo.getNombre();
		this.dni = pojo.getDni();
		this.patente = pojo.getPatente();
		this.saldo = pojo.getSaldo();
		this.password = pojo.getPassword();

	}

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

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return dni + " - " + nombre + " " + apellido;
	}

}
