package com.tuti.servicios;

import com.tuti.dto.EstacionamientoDTO;

public interface EstacionamientoService {
	
	
	/**
	 * Servicio para estacionar vehiculo
	 * @return void
	 */
	public void estacionarVehiculo(String patente, String password) throws Exception;

	
	 public void liberarVehiculo(String patente, String password) throws Exception;
	 
	
	 public EstacionamientoDTO consultarEstado(String patente) throws Exception;

}
