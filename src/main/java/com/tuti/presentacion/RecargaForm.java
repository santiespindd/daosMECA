package com.tuti.presentacion;

import java.math.BigDecimal;

import com.tuti.entidades.Comercio;
import com.tuti.entidades.Recarga;
import com.tuti.entidades.Usuario;
import com.tuti.servicios.UsuarioService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RecargaForm {
	
	@NotNull(message = "El id no puede ser nulo")
	private Long id;
	
	@NotNull(message = "El dni de usuario no puede ser nulo")
	private Long usuarioDni;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String patente;
	
	@NotNull(message = "El cuit de comercio no puede ser nulo")
	private Long comercioCuit;
	
	private BigDecimal importe;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUsuarioDni() {
		return usuarioDni;
	}
	public void setUsuarioDni(Long usuarioDni) {
		this.usuarioDni = usuarioDni;
	}
	public String getPatente() {
		return patente;
	}
	public void setPatente(String patente) {
		this.patente = patente;
	}
	
	public Long getComercioCuit() {
		return comercioCuit;
	}
	public void setComercioCuit(Long comercioCuit) {
		this.comercioCuit = comercioCuit;
	}
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	
}
