package com.tuti.servicios;

import com.tuti.dto.EstacionamientoDTO;

public interface EstacionamientoService {
	
	
	/**
	 * Servicio para estacionar vehiculo
	 * @return void
	 */
	public EstacionamientoDTO estacionarVehiculo(String patente, String password) throws Exception;

	
	 public  EstacionamientoDTO liberarVehiculo(String patente, String password) throws Exception;
	 
	
	 public EstacionamientoDTO consultarEstado(String patente) throws Exception;
	 
	 

}
