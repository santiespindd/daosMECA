package com.tuti.accesoADatos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tuti.entidades.Recarga;

@Repository
public interface RecargaDao extends JpaRepository<Recarga, Long>{
	
	List<Recarga>findByUsuarioDni(Long dni);
	List<Recarga>findByPatente(String patente);
	List<Recarga>findByComercioCuit(Long comercioCuit);
	
}
