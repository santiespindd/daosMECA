package com.tuti.entidades;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Recarga extends RepresentationModel<Recarga>{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private BigDecimal importe;
	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Comercio comercio;
	@ManyToOne
	private Estacionamiento estacionamiento;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
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
	public Estacionamiento getEstacionamiento() {
		return estacionamiento;
	}
	public void setEstacionamiento(Estacionamiento estacionamiento) {
		this.estacionamiento = estacionamiento;
	}
	
	
	
}
