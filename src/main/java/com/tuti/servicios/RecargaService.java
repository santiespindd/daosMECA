package com.tuti.servicios;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.tuti.entidades.Recarga;
import com.tuti.exception.Excepcion;

public interface RecargaService {
	
	/**
	 * Obtiene una recarga por su patente
	 * @param patente
	 * @return
	 */
	List<Recarga>getRecargaByPatente(String patente);
	
	/**
	 * Obtiene una recarga por dni de usuario
	 * @param dni
	 * @return
	 */
	List<Recarga>getRecargaByUsuarioDni(Long dni);
	
	/**
	 * Obtiene una recarga por cuit de comercio
	 * @param comercioCuit
	 * @return
	 */
	List<Recarga>getRecargaByComercioCuit(Long comercioCuit);
	
	/**
	 * Obtiene todas las recargas
	 * @return todas las recargas
	 */
	List<Recarga>getAllRecargas();
	
	/**
	 * Inserta una recarga
	 * @param usuarioDni 
	 * @param id 
	 * @param patente, comercioCuit, importe
	 * @return
	 */
	Recarga realizarRecarga(Long id, Long usuarioDni, String patente, Long comercioCuit, BigDecimal importe) throws Excepcion;
	
	
}
