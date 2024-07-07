package com.tuti.accesoADatos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tuti.entidades.Recarga;

@Repository
public interface RecargaDao extends JpaRepository<Recarga, Long>{
	
	Optional<Recarga>findByUsuarioDni(Long dni);
	Optional<Recarga>findByPatente(String patente);
	Optional<Recarga>findByComercioCuit(Long comercioCuit);
	
}
