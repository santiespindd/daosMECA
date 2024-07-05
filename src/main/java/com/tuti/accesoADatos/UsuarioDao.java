package com.tuti.accesoADatos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tuti.entidades.Usuario;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Long> {

	public List<Usuario> findByApellidoOrNombre(String apellido, String nombre);
	
	public Optional<Usuario> findByPatente(String patente);
}
