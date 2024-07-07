package com.tuti.entidades;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

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
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="usuario_dni")
	private Usuario usuario;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="comercio_id")
	private Comercio comercio;
	
	@NotNull
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
