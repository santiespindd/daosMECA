package com.tuti.servicios;

import java.util.List;
import java.util.Optional;

import com.tuti.entidades.Comercio;
import com.tuti.exception.Excepcion;

public interface ComercioService {

    /**
     * Devuelve la lista completa de comercios.
     * @return Lista de comercios.
     */
    public List<Comercio> getAll();

    /**
     * Obtiene un comercio a partir de su CUIT.
     * @param cuit
     * @return Comercio correspondiente al CUIT proporcionado.
     */
    public Optional<Comercio> getByCuit(Long cuit);

    /**
     * Obtiene un comercio a partir de su razón social.
     * @param razonSocial
     * @return Comercio correspondiente a la razón social proporcionada.
     */
    public Optional<Comercio> getByRazonSocial(String razonSocial);

    /**
     * Actualiza los datos de un comercio.
     * @param comercio
     * @throws Excepcion Si el comercio no existe o si hay violaciones de validación.
     */
    public void update(Comercio comercio) throws Excepcion;

    /**
     * Inserta un nuevo comercio.
     * @param comercio
     * @throws Excepcion Si hay violaciones de validación o si el comercio ya existe.
     */
    public void insert(Comercio comercio) throws Excepcion;

    /**
     * Suspende un comercio cambiando su estado a "suspendido" a partir de su CUIT.
     * @param cuit
     * @throws Excepcion Si el comercio no existe.
     */
    public void suspend(Long cuit) throws Excepcion;

    /**
     * Elimina un comercio del sistema.
     * @param id Identificador del comercio a eliminar.
     */
    public void delete(Long id);

    /**
     * Filtra los comercios por CUIT o razón social.
     * @param cuit
     * @param razonSocial
     * @return Lista de comercios que coinciden con los filtros proporcionados.
     */
    public List<Comercio> filtrar(Long cuit, String razonSocial);
}


