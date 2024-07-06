package com.tuti.presentacion;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuti.dto.UsuarioResponseDTO;
import com.tuti.entidades.Usuario;
import com.tuti.exception.Excepcion;
import com.tuti.presentacion.error.MensajeError;
import com.tuti.servicios.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Buscar, agregar, actualizar y borrar usuarios del sistema")
public class UsuarioRestController {

	@Autowired
	private UsuarioService service;

	/**
	 * Permite filtrar personas. Ej1 curl --location --request GET
	 * 'http://localhost:8081/personas?apellido=Perez&&nombre=Juan' Lista las
	 * personas llamadas Perez, Juan Ej2 curl --location --request GET
	 * 'http://localhost:8081/personas?apellido=Perez' Lista aquellas personas de
	 * apellido PErez Ej3 curl --location --request GET
	 * 'http://localhost:8081/personas' Lista todas las personas
	 * 
	 * @param apellido
	 * @param nombre
	 * @return
	 * @throws Excepcion
	 */
	@Operation(summary = "Filtrar usuarios por apellido y/o nombre (o mostrar todos si no hay parámetros)")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<UsuarioResponseDTO> filtrarUsuarios(@RequestParam(name = "apellido", required = false) String apellido,
			@RequestParam(name = "nombre", required = false) @Size(min = 1, max = 20) String nombre) throws Excepcion {

		List<Usuario> usuarios = service.filtrar(apellido, nombre);
		List<UsuarioResponseDTO> dtos = new ArrayList<UsuarioResponseDTO>();
		for (Usuario pojo : usuarios) {

			dtos.add(buildResponse(pojo));
		}
		return dtos;

	}

	/**
	 * Busca una persona a partir de su dni curl --location --request GET
	 * 'http://localhost:8081/personas/27837171'
	 * 
	 * @param dni DNI de la persona buscada
	 * @return Persona encontrada o Not found en otro caso
	 * @throws Excepcion
	 */
	@Operation(summary = "Buscar un usuario por DNI")
	@GetMapping(value = "/{dni}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long dni) throws Excepcion {
		Optional<Usuario> rta = service.getById(dni);
		if (rta.isPresent()) {
			Usuario pojo = rta.get();
			return new ResponseEntity<UsuarioResponseDTO>(buildResponse(pojo), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	/**
	 * Inserta una nueva persona en la base de datos curl --location --request POST
	 * 'http://localhost:8081/personas' --header 'Accept: application/json' --header
	 * 'Content-Type: application/json' --data-raw '{ "dni": 27837171, "apellido":
	 * "perez", "nombre": "juan", "idCiudad": 2 }'
	 * 
	 * @param p Persona a insertar
	 * @return Persona insertada o error en otro caso
	 * @throws Exception
	 */
	@Operation(summary = "Agregar un nuevo usuario")
	@PostMapping
	public ResponseEntity<Object> guardar(@Valid @RequestBody UsuarioForm form, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			// Dos alternativas:
			// throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
			// this.formatearError(result));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.formatearError(result));
		}

		Usuario u = form.toPojo();

		// ahora inserto el cliente
		service.insert(u);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{dni}").buildAndExpand(u.getDni())
				.toUri(); // Por convención en REST, se devuelve la url del recurso recién creado

		return ResponseEntity.created(location).build();// 201 (Recurso creado correctamente)

	}

	/**
	 * Modifica una persona existente en la base de datos: curl --location --request
	 * PUT 'http://localhost:8081/personas/27837176' --header 'Accept:
	 * application/json' --header 'Content-Type: application/json' --data-raw '{
	 * "apellido": "Perez", "nombre": "Juan Martin" "idCiudad": 1 }'
	 * 
	 * @param p Persona a modificar
	 * @return Persona Editada o error en otro caso
	 * @throws Excepcion
	 */
	@Operation(summary = "Actualizar un usuario por DNI")
	@PutMapping("/{dni}")
	public ResponseEntity<Object> actualizar(@RequestBody UsuarioForm form, @PathVariable long dni) throws Exception {
		Optional<Usuario> rta = service.getById(dni);
		if (rta.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró un usuario con ese DNI");

		else {
			Usuario u = form.toPojo();
			if (!u.getDni().equals(dni))// El dni es el identificador, con lo cual es el único dato que no permito
										// modificar
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(getError("03", "Dato no editable", "No se puede modificar el DNI del usuario"));
			service.update(u);
			return ResponseEntity.ok(buildResponse(u));
		}

	}

	/**
	 * Borra la persona con el dni indicado curl --location --request DELETE
	 * 'http://localhost:8081/personas/27837176'
	 * 
	 * @param dni Dni de la persona a borrar
	 * @return ok en caso de borrar exitosamente la persona, error en otro caso
	 */
	@Operation(summary = "Borrar un usuario por DNI")
	@DeleteMapping("/{dni}")
	public ResponseEntity<String> eliminar(@PathVariable Long dni) {
		if (!service.getById(dni).isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró un usuario con ese DNI");
		service.delete(dni);

		return ResponseEntity.ok().build();

	}

	/**
	 * Métdo auxiliar que toma los datos del pojo y construye el objeto a devolver
	 * en la response, con su hipervinculos
	 * 
	 * @param pojo
	 * @return
	 * @throws Excepcion
	 */
	private UsuarioResponseDTO buildResponse(Usuario pojo) throws Excepcion {
		try {
			UsuarioResponseDTO dto = new UsuarioResponseDTO(pojo);
			// Self link
			Link selfLink = WebMvcLinkBuilder.linkTo(UsuarioRestController.class).slash(pojo.getDni()).withSelfRel();
			// Method link: Link al servicio de estacionamiento
			Link estadoLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstacionamientoController.class)
			.consultarEstado(pojo.getPatente()))
			.withRel("estado-estacionamiento");
			dto.add(selfLink);
			dto.add(estadoLink);
			return dto;
		} catch (Exception e) {
			throw new Excepcion(e.getMessage(), 500);
		}
	}

	private String formatearError(BindingResult result) throws JsonProcessingException {
//		primero transformamos la lista de errores devuelta por Java Bean Validation
		List<Map<String, String>> errores = result.getFieldErrors().stream().map(err -> {
			Map<String, String> error = new HashMap<>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}).collect(Collectors.toList());
		MensajeError e1 = new MensajeError();
		e1.setCodigo("01");
		e1.setMensajes(errores);

		// ahora usamos la librería Jackson para pasar el objeto a json
		ObjectMapper maper = new ObjectMapper();
		String json = maper.writeValueAsString(e1);
		return json;
	}

	private String getError(String code, String err, String descr) throws JsonProcessingException {
		MensajeError e1 = new MensajeError();
		e1.setCodigo(code);
		ArrayList<Map<String, String>> errores = new ArrayList<>();
		Map<String, String> error = new HashMap<String, String>();
		error.put(err, descr);
		errores.add(error);
		e1.setMensajes(errores);

		// ahora usamos la librería Jackson para pasar el objeto a json
		ObjectMapper maper = new ObjectMapper();
		String json = maper.writeValueAsString(e1);
		return json;
	}

}
