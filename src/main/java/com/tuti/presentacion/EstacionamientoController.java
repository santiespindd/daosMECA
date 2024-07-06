package com.tuti.presentacion;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuti.dto.EstacionamientoDTO;
import com.tuti.exception.EstacionamientoException;
import com.tuti.exception.Excepcion;
import com.tuti.presentacion.error.MensajeError;
import com.tuti.servicios.EstacionamientoServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estacionamiento")
public class EstacionamientoController {

    @Autowired
    private EstacionamientoServiceImpl estacionamientoService;
    
    
    /**
	 * Permite consultar estado de un vehiculo
	 * @param patente
	 * @return
	 * @throws Excepcion
	 */
    @GetMapping("/{patente}")
    public ResponseEntity<EntityModel<EstacionamientoDTO>> consultarEstado(@PathVariable String patente)  throws Exception {
        try {
            EstacionamientoDTO dto = estacionamientoService.consultarEstado(patente);
            EntityModel<EstacionamientoDTO> resource = EntityModel.of(dto);
            
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstacionamientoController.class).consultarEstado(patente)).withSelfRel();
            resource.add(selfLink);
            
            Link usuarioLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioRestController.class).getById(dto.getUsuarioId())).withRel("usuario");
            resource.add(usuarioLink);
           
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
  	 * Permite estacionar un vehiculo
  	 * @param patente
  	 * @param dni
  	 * @return
  	 * @throws Excepcion
  	 */
    @Operation(summary = "Estacionar vehiculo")
    @PostMapping("/estacionar")
    public ResponseEntity<Object> estacionarVehiculo(@RequestParam String patente, @RequestParam String password) throws Exception {
    	
    	try {
    		 EstacionamientoDTO dto = estacionamientoService.estacionarVehiculo(patente.toUpperCase(), password);

    	    	
    	      	
    	   	   EntityModel<EstacionamientoDTO> resource = EntityModel.of(dto);
    	   	   
    	   	   Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstacionamientoController.class).estacionarVehiculo(patente,password)).withSelfRel();
    	          resource.add(selfLink);
    	   	
    	       Link usuarioLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioRestController.class).getById(dto.getUsuarioId())).withRel("usuario");
    	       resource.add(usuarioLink);
    	          
    	           return ResponseEntity.ok(resource);
    		
    	}catch (EstacionamientoException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
    	 
      
    }
    /**
  	 * Permite liberar un vehiculo estacionado
  	 * @param patente
  	 * @param dni
  	 * @return Vehiculo liberado
     * @throws Exception 
  
  	 */
    @PutMapping("/liberar")
    public ResponseEntity<Object> liberarVehiculo(@RequestParam String patente, @RequestParam String password) throws Exception {
    	
    	EstacionamientoDTO dto=  estacionamientoService.liberarVehiculo(patente.toUpperCase(), password);
    	
    	   EntityModel<EstacionamientoDTO> resource = EntityModel.of(dto);
    	   
    	   Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstacionamientoController.class).liberarVehiculo(patente,password)).withSelfRel();
           resource.add(selfLink);
    	
        Link usuarioLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioRestController.class).getById(dto.getUsuarioId())).withRel("usuario");
        resource.add(usuarioLink);
           
            return ResponseEntity.ok(resource);
       
    }
    
    @ExceptionHandler(EstacionamientoException.class)
    public ResponseEntity<Object> handleEstacionamientoException(EstacionamientoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
