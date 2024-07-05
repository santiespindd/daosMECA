package com.tuti.accesoADatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuti.entidades.Recarga;

@Repository
public interface RecargaDao extends JpaRepository<Recarga, Long>{

}
