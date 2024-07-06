package com.tuti.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tuti.dto.EstacionamientoDTO;
import com.tuti.exception.EstacionamientoException;
import com.tuti.exception.Excepcion;
import com.tuti.servicios.EstacionamientoServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/estacionamiento")
@Tag(name = "Estacionamiento", description = "Estacionar un vehículo, liberar el estacionamiento y consultar estado")
public class EstacionamientoController {

    @Autowired
    private EstacionamientoServiceImpl estacionamientoService;
    
    
    /**
	 * Permite consultar estado de un vehiculo
	 * @param patente
	 * @return
	 * @throws Excepcion
	 */
    @Operation(summary = "Consultar el estado de un vehiculo a través de su patente")
    @GetMapping("/{patente}")
    public ResponseEntity<Object> consultarEstado(@PathVariable String patente)  throws Exception {
        try {
            EstacionamientoDTO dto = estacionamientoService.consultarEstado(patente);
            EntityModel<EstacionamientoDTO> resource = EntityModel.of(dto);
            
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstacionamientoController.class).consultarEstado(patente)).withSelfRel();
            resource.add(selfLink);
            
            Link usuarioLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioRestController.class).getById(dto.getUsuarioId())).withRel("usuario");
            resource.add(usuarioLink);
           
            return ResponseEntity.ok(resource);
        } catch (EstacionamientoException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
    }
    /**
  	 * Permite estacionar un vehiculo
  	 * @param patente
  	 * @param dni
  	 * @return
  	 * @throws Excepcion
  	 */
    @Operation(summary = "Estacionar vehiculo ingresando contraseña y patente")
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
    @Operation(summary = "Liberar vehiculo ingresando contraseña y patente")
    @PutMapping("/liberar")
    public ResponseEntity<Object> liberarVehiculo(@RequestParam String patente, @RequestParam String password) throws Exception {
    	
    	try {
    		EstacionamientoDTO dto=  estacionamientoService.liberarVehiculo(patente.toUpperCase(), password);
        	
     	   EntityModel<EstacionamientoDTO> resource = EntityModel.of(dto);
     	   
     	   Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstacionamientoController.class).liberarVehiculo(patente,password)).withSelfRel();
            resource.add(selfLink);
     	
         Link usuarioLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioRestController.class).getById(dto.getUsuarioId())).withRel("usuario");
         resource.add(usuarioLink);
            
             return ResponseEntity.ok(resource);
    		
    	}catch (EstacionamientoException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
    	
       
    }
    
    @ExceptionHandler(EstacionamientoException.class)
    public ResponseEntity<Object> handleEstacionamientoException(EstacionamientoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
