package com.tuti.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

public class RecargaResponseDTO extends RepresentationModel<RecargaResponseDTO>{
	
	private Long id;
	private Long usuarioDni;
	private String patente;
	private Long comercioId;
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
	public Long getComercioId() {
		return comercioId;
	}
	public void setComercioId(Long comercioId) {
		this.comercioId = comercioId;
	}
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	
	
}
