package com.tuti.accesoADatos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.tuti.entidades.Estacionamiento;


public interface EstacionamientoDAO  extends JpaRepository<Estacionamiento, Long> {
     Optional<Estacionamiento> findByPatente(String patente);
}
