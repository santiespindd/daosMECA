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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Comercio", description = "API para gestionar comercios")
public class ComercioRestController {

    @Autowired
    private ComercioService comercioService;

    @Operation(summary = "Obtener todos los comercios", 
               description = "Retorna una lista de todos los comercios registrados con enlaces HATEOAS.")
    @GetMapping
    public ResponseEntity<List<EntityModel<Comercio>>> getAll() {
        List<Comercio> comercios = comercioService.getAll();
        List<EntityModel<Comercio>> comercioModels = comercios.stream()
                .map(comercio -> EntityModel.of(comercio, createComercioLinks(comercio)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(comercioModels, HttpStatus.OK);
    }

    @Operation(summary = "Obtener comercio por CUIT", 
               description = "Busca y retorna un comercio específico basado en su CUIT.")
    @GetMapping("/cuit/{cuit}")
    public ResponseEntity<EntityModel<Comercio>> getByCuit(@PathVariable Long cuit) {
        Optional<Comercio> comercio = comercioService.getByCuit(cuit);
        return comercio.map(value -> ResponseEntity.ok(EntityModel.of(value, createComercioLinks(value))))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener comercio por razón social", 
               description = "Busca y retorna un comercio específico basado en su razón social.")
    @GetMapping("/razonSocial/{razonSocial}")
    public ResponseEntity<EntityModel<Comercio>> getByRazonSocial(@PathVariable String razonSocial) {
        Optional<Comercio> comercio = comercioService.getByRazonSocial(razonSocial);
        return comercio.map(value -> ResponseEntity.ok(EntityModel.of(value, createComercioLinks(value))))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Insertar un nuevo comercio", 
               description = "Crea un nuevo registro de comercio con los datos proporcionados.")
    @PostMapping
    public ResponseEntity<?> insertComercio(@Valid @RequestBody ComercioForm comercioForm, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Error de validación en el formulario.");
        }

        try {
            comercioForm.setEstado("autorizado");
            Comercio comercio = comercioForm.toPojo();
            comercioService.insert(comercio);

            URI location = new URI("/comercios/" + comercio.getId());
            return ResponseEntity.created(location).body(comercio);
        } catch (Excepcion e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizar comercio por CUIT", 
               description = "Actualiza los datos de un comercio existente identificado por su CUIT.")
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
            comercioToUpdate.setRazonSocial(comercioForm.getRazonSocial());
            comercioToUpdate.setDireccion(comercioForm.getDireccion());

            comercioService.update(comercioToUpdate);
            return ResponseEntity.ok(EntityModel.of(comercioToUpdate, createComercioLinks(comercioToUpdate)));
        } catch (Excepcion e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Suspender comercio", 
               description = "Cambia el estado de un comercio a suspendido basado en su CUIT.")
    @DeleteMapping("/{cuit}")
    public ResponseEntity<?> suspendComercio(@PathVariable Long cuit) {
        try {
            comercioService.suspend(cuit);
            return ResponseEntity.ok("Comercio suspendido con éxito.");
        } catch (Excepcion e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar comercios", 
               description = "Filtra y retorna comercios basados en CUIT o razón social.")
    @GetMapping("/buscar")
    public ResponseEntity<List<EntityModel<Comercio>>> buscarComercios(@RequestParam(required = false) Long cuit,
                                                                        @RequestParam(required = false) String razonSocial) {
        List<Comercio> comercios = comercioService.filtrar(cuit, razonSocial);
        List<EntityModel<Comercio>> comercioModels = comercios.stream()
                .map(comercio -> EntityModel.of(comercio, createComercioLinks(comercio)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(comercioModels);
    }

    private Link[] createComercioLinks(Comercio comercio) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComercioRestController.class).getByCuit(comercio.getCuit())).withSelfRel();
        Link allComerciosLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComercioRestController.class).getAll()).withRel("all-comercios");
        return new Link[]{selfLink, allComerciosLink};
    }
}
