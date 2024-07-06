package com.tuti.presentacion;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import com.tuti.entidades.Recarga;
import com.tuti.servicios.ComercioService;
import com.tuti.servicios.EstacionamientoService;
import com.tuti.servicios.RecargaService;
import com.tuti.servicios.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/recargas")
@Tag(name = "Recargas", description = "Buscar, agregar y actualizar recargas")
public class RecargaRestController {
	
	@Autowired
	private RecargaService service;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ComercioService comercioService;
	@Autowired
	private EstacionamientoService estacionamientoService;
	
	@Operation(summary = "Mostrar todas las recargas")
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Recarga>getAll(){
		List<Recarga>recargas=service.getAll();
		for	(Recarga recarga:recargas) {
			addHateoasLinks(recarga);
		}
		return recargas;
	}
	@Operation(summary = "Obtener recargas por id")
	@GetMapping(value="/{id}", produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Recarga>getById(@PathVariable Long id){
		Optional<Recarga>rta=service.getById(id);
		if(rta.isPresent()) {
			Recarga recarga = rta.get();
			addHateoasLinks(recarga);
			return new ResponseEntity<>(recarga, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	@Operation(summary = "Obtener recargas por DNI de usuario")
    @GetMapping("/usuario/{dni}")
    public ResponseEntity<List<Recarga>> getRecargasByUsuarioDni(@PathVariable Long dni) {
        List<Recarga> recargas = service.getByUsuarioDni(dni);
        recargas.forEach(this::addHateoasLinks); // Agregar enlaces HATEOAS a cada recarga
        return ResponseEntity.ok(recargas);
    }
	@Operation(summary = "Obtener recargas por CUIT de comercio")
    @GetMapping("/comercio/{cuit}")
    public ResponseEntity<List<Recarga>> getRecargasByComercioCuit(@PathVariable Long cuit) {
        List<Recarga> recargas = service.getByComercioCuit(cuit);
        recargas.forEach(this::addHateoasLinks); // Agregar enlaces HATEOAS a cada recarga
        return ResponseEntity.ok(recargas);
    }
	@Operation(summary = "Obtener recargas por patente de estacionamiento")
    @GetMapping("/estacionamiento/{patente}")
    public ResponseEntity<List<Recarga>> getRecargasByEstacionamientoPatente(@PathVariable String patente) {
        List<Recarga> recargas = service.getByEstacionamientoPatente(patente);
        recargas.forEach(this::addHateoasLinks); // Agregar enlaces HATEOAS a cada recarga
        return ResponseEntity.ok(recargas);
    }
	@Operation(summary = "Agregar nueva recarga")
	@PostMapping
	public ResponseEntity<Object>insert(@Valid @RequestBody Recarga recarga, BindingResult result) throws Exception{
		if(result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());			
		}
		service.insert(recarga);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id})").buildAndExpand(recarga.getId()).toUri();
		return ResponseEntity.created(location).build();		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String>delete(@PathVariable Long id){
		if(!service.getById(id).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una recarga con ese id");		
		}
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	private void addHateoasLinks(Recarga recarga) {
		try	{
		Link selfLink=WebMvcLinkBuilder.linkTo(RecargaRestController.class).slash(recarga.getId()).withSelfRel();
		Link usuarioLink=WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioRestController.class).getById(recarga.getUsuario().getDni())).withRel("usuario");
		Link comercioLink=WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComercioRestController.class).getByCuit(recarga.getComercio().getId())).withRel("comercio");
	//	Link estacionamientoLink=WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstacionamientoController.class).(recarga.getEstacionamiento().getPatente())).withRel("estacionamiento");
		
		recarga.add(selfLink);
		recarga.add(usuarioLink);
		recarga.add(comercioLink);
	//	recarga.add(estacionamientoLink);
		} catch (Exception e) {           
            e.printStackTrace();
            throw new RuntimeException("Error al agregar enlaces HATEOAS a la recarga");
        }
		
		
	}
}
