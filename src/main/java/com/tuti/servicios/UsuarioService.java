package com.tuti.servicios;

import java.util.List;
import java.util.Optional;

import com.tuti.entidades.Usuario;


public interface UsuarioService {
	
	/**
	 * Devuelve la lista completa de personas
	 * @return Lista de personas
	 */
	public List<Usuario> getAll();

	/**
	 * Obtiene una persona a partir de su identidicador
	 * @param id
	 * @return
	 */
	public Optional<Usuario> getById(Long id);

	/**
	 * Actualiza datos de una persona
	 * @param u
	 */
	public void update(Usuario u);

	/**
	 * Inserta una nueva persona
	 * @param u
	 * @throws Exception
	 */
	public void insert(Usuario u) throws Exception;

	/**
	 * Elimina una persona del sistema
	 * @param id dni de la persona a eliminar
	 */
	public void delete(Long id);

	public List<Usuario> filtrar(String apellido, String nombre);

	public Optional<Usuario> getByPatente(String patente);
}
