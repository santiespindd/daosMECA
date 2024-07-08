package com.tuti.presentacion;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.tuti.dto.RecargaResponseDTO;
import com.tuti.entidades.Recarga;
import com.tuti.exception.Excepcion;
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
	private RecargaService recargaService;
	@Operation(summary = "Obtener todas las recargas")
	@GetMapping("/recargas")
	public ResponseEntity<List<RecargaResponseDTO>> getAllRecargas() {
	    List<Recarga> recargas = recargaService.getAllRecargas();
	    
	    if (recargas.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    List<RecargaResponseDTO> recargaDTOs = recargas.stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());
	    
	    return ResponseEntity.ok(recargaDTOs);
	}
	
	@Operation(summary = "Obtener recargas por patente")
	@GetMapping("/{patente}")
	public ResponseEntity<List<RecargaResponseDTO>>getRecargaByPatente(@RequestParam String patente) {
		List<Recarga> recargas = recargaService.getRecargaByPatente(patente);
		if (recargas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		List<RecargaResponseDTO> recargaDTOs = recargas.stream()	
				.map(this::convertToDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(recargaDTOs);
	}
	@Operation(summary = "Obtener recargas por dni de usuario")
	@GetMapping("/{dni}")
    public ResponseEntity<List<RecargaResponseDTO>> getRecargaByUsuarioDni(@RequestParam Long dni) {
		List<Recarga> recargas = recargaService.getRecargaByUsuarioDni(dni);
		if (recargas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		List<RecargaResponseDTO> recargaDTOs = recargas.stream()	
				.map(this::convertToDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(recargaDTOs);
    }
	@Operation(summary = "Obtener recargas por cuit de comercio")
	@GetMapping("/{cuit}")
	public ResponseEntity<List<RecargaResponseDTO>>getRecargaByComercioCuit(@RequestParam Long comercioCuit){
		List<Recarga> recargas = recargaService.getRecargaByComercioCuit(comercioCuit);
		if (recargas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		List<RecargaResponseDTO> recargaDTOs = recargas.stream()	
				.map(this::convertToDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(recargaDTOs);
	}
	@Operation(summary = "Insertar una nueva recarga")
    @PostMapping
    public ResponseEntity<?> realizarRecarga(
            @RequestParam String patente,
            @RequestParam Long comercioCuit,
            @RequestParam BigDecimal importe) {
        try {
            Recarga recarga = recargaService.realizarRecarga(patente, comercioCuit, importe);
            RecargaResponseDTO recargaDTO = convertToDTO(recarga);
            return ResponseEntity.ok(recargaDTO);
        } catch (Excepcion e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }
	
	private RecargaResponseDTO convertToDTO(Recarga recarga) {
		RecargaResponseDTO dto = new RecargaResponseDTO();
		dto.setId(recarga.getId());
		dto.setUsuarioDni(recarga.getUsuario().getDni());
		dto.setPatente(recarga.getPatente());
		dto.setComercioId(recarga.getComercio().getId());
		dto.setImporte(recarga.getImporte());
		
			Link selfLink = WebMvcLinkBuilder.linkTo(RecargaRestController.class).slash(recarga.getId()).withSelfRel();
			dto.add(selfLink);
			
			try {
			Link usuarioLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioRestController.class).getById(recarga.getUsuario().getDni())).withRel("usuario");
			dto.add(usuarioLink);
			}catch (Exception e){
				
			}
			try {
			Link comercioLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComercioRestController.class).getByCuit(recarga.getComercio().getCuit())).withRel("comercio");		
			dto.add(comercioLink);		
			}catch (Exception e){
				
			}
			return dto;

		
		
		
		
	}
}
