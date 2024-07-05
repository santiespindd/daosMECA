package com.tuti.accesoADatos;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tuti.entidades.Comercio;

public interface ComercioDAO extends JpaRepository<Comercio, Long> {
    
    @Query("SELECT c FROM Comercio c WHERE c.razonSocial LIKE %?1%")
    Collection<Comercio> findComerciosLike(String razonSocial);
    
    @Query("SELECT c FROM Comercio c WHERE c.cuit = ?1")
    Optional<Comercio> findComercioByCuit(Long cuit);
    
    @Query("SELECT c FROM Comercio c WHERE c.razonSocial = ?1")
    Optional<Comercio> findComercioByRazonSocial(String razonSocial);

    @Query("SELECT c FROM Comercio c WHERE c.razonSocial = ?1 OR c.cuit = ?2")
    List<Comercio> findByRazonSocialOrCuit(String razonSocial, Long cuit);
}
