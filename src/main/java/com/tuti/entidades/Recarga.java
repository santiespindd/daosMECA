package com.tuti.entidades;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Recarga{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "El usuario no puede ser nulo")
	@ManyToOne
	@JoinColumn(name="usuario_dni")
	private Usuario usuario;
	
	@NotNull(message = "El cuit no puede ser nulo")
	@ManyToOne
	@JoinColumn(name="comercio_id")
	private Comercio comercio;
	
	@NotNull(message = "La patente no puede ser nula")
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String patente;
	
	@NotNull
	private BigDecimal importe;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Comercio getComercio() {
		return comercio;
	}
	public void setComercio(Comercio comercio) {
		this.comercio = comercio;
	}
	public String getPatente() {
		return patente;
	}
	public void setPatente(String patente) {
		this.patente = patente;
	}
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	
	
}
