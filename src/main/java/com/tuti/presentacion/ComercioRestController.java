package com.tuti.presentacion;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.tuti.entidades.Comercio;
import com.tuti.servicios.ComercioService;

import jakarta.validation.Valid;

import com.tuti.exception.Excepcion;

/**
 * Controlador REST para gestionar operaciones relacionadas con los comercios.
 * <p>
 * Este controlador permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre entidades de comercio, incluyendo búsqueda por CUIT y razón social.
 * </p>
 * <p>
 * Todos los métodos manejan respuestas JSON y cumplen con los estándares de HATEOAS.
 * </p>
 */
@RestController
@RequestMapping("/comercios")
public class ComercioRestController {

    @Autowired
    private ComercioService comercioService;

    /**
     * Obtiene todos los comercios.
     *
     * @return ResponseEntity con la lista de todos los comercios y enlaces HATEOAS.
     */
    @GetMapping
    public ResponseEntity<List<EntityModel<Comercio>>> getAll() {
        List<Comercio> comercios = comercioService.getAll();
        List<EntityModel<Comercio>> comercioModels = comercios.stream()
                .map(comercio -> EntityModel.of(comercio, createComercioLinks(comercio)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(comercioModels, HttpStatus.OK);
    }

    /**
     * Obtiene un comercio por su CUIT.
     *
     * @param cuit El CUIT del comercio a buscar.
     * @return ResponseEntity con el comercio encontrado y enlaces HATEOAS, o 404 si no se encuentra.
     */
    @GetMapping("/cuit/{cuit}")
    public ResponseEntity<EntityModel<Comercio>> getByCuit(@PathVariable Long cuit) {
        Optional<Comercio> comercio = comercioService.getByCuit(cuit);
        return comercio.map(value -> ResponseEntity.ok(EntityModel.of(value, createComercioLinks(value))))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Obtiene un comercio por su razón social.
     *
     * @param razonSocial La razón social del comercio a buscar.
     * @return ResponseEntity con el comercio encontrado y enlaces HATEOAS, o 404 si no se encuentra.
     */
    @GetMapping("/razonSocial/{razonSocial}")
    public ResponseEntity<EntityModel<Comercio>> getByRazonSocial(@PathVariable String razonSocial) {
        Optional<Comercio> comercio = comercioService.getByRazonSocial(razonSocial);
        return comercio.map(value -> ResponseEntity.ok(EntityModel.of(value, createComercioLinks(value))))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Inserta un nuevo comercio.
     *
     * @param comercioForm El formulario con los datos del nuevo comercio.
     * @param result       Resultado de la validación del formulario.
     * @return ResponseEntity con el comercio creado y enlaces HATEOAS, o mensaje de error si falla la validación.
     */
    @PostMapping
    public ResponseEntity<?> insertComercio(@Valid @RequestBody ComercioForm comercioForm, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Error de validación en el formulario.");
        }

        try {
            // Establecer estado por defecto
            comercioForm.setEstado("autorizado");

            Comercio comercio = comercioForm.toPojo(); // Esto debe manejar la creación del comercio
            comercioService.insert(comercio);

            URI location = new URI("/comercios/" + comercio.getId());
            return ResponseEntity.created(location).body(comercio);
        } catch (Excepcion e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualiza un comercio existente por su CUIT.
     *
     * @param cuit         El CUIT del comercio a actualizar.
     * @param comercioForm El formulario con los datos actualizados del comercio.
     * @param result       Resultado de la validación del formulario.
     * @return ResponseEntity con el comercio actualizado y enlaces HATEOAS, o un mensaje de error si falla la validación o la operación.
     */

    @PutMapping("/cuit/{cuit}")
    public ResponseEntity<?> updateComercioByCuit(@PathVariable Long cuit, @Valid @RequestBody ComercioForm comercioForm, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Error de validación en el formulario.");
        }

        try {
            Optional<Comercio> existingComercio = comercioService.getByCuit(cuit);
            if (existingComercio.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Comercio comercioToUpdate = existingComercio.get();
            // Actualizar solo los campos permitidos
            comercioToUpdate.setRazonSocial(comercioForm.getRazonSocial());
            comercioToUpdate.setDireccion(comercioForm.getDireccion());
            // No modificar el estado aquí para evitar cambios no deseados

            comercioService.update(comercioToUpdate);
            return ResponseEntity.ok(EntityModel.of(comercioToUpdate, createComercioLinks(comercioToUpdate)));
        } catch (Excepcion e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Suspende un comercio por su ID.
     *
     * @param id El ID del comercio a suspender.
     * @return ResponseEntity con estado 204 No Content si se suspende correctamente, o 404 si no se encuentra.
     */
    @DeleteMapping("/{cuit}")
    public ResponseEntity<?> suspendComercio(@PathVariable Long cuit) {
        try {
            comercioService.suspend(cuit);
            return ResponseEntity.ok("Comercio suspendido con éxito.");
        } catch (Excepcion e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * Busca comercios filtrando por CUIT o razón social.
     *
     * @param cuit         El CUIT del comercio a buscar (opcional).
     * @param razonSocial  La razón social del comercio a buscar (opcional).
     * @return ResponseEntity con la lista de comercios encontrados y enlaces HATEOAS.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<EntityModel<Comercio>>> buscarComercios(@RequestParam(required = false) Long cuit,
                                                                        @RequestParam(required = false) String razonSocial) {
        List<Comercio> comercios = comercioService.filtrar(cuit, razonSocial);
        List<EntityModel<Comercio>> comercioModels = comercios.stream()
                .map(comercio -> EntityModel.of(comercio, createComercioLinks(comercio)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(comercioModels);
    }

    /**
     * Crea los enlaces HATEOAS para un comercio dado.
     *
     * @param comercio El comercio para el cual se generarán los enlaces.
     * @return Arreglo de enlaces HATEOAS.
     */
    private Link[] createComercioLinks(Comercio comercio) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComercioRestController.class).getByCuit(comercio.getCuit())).withSelfRel();
        Link allComerciosLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComercioRestController.class).getAll()).withRel("all-comercios");
        return new Link[]{selfLink, allComerciosLink};
    }
}
