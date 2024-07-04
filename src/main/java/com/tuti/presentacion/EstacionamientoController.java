package com.tuti.presentacion;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuti.dto.EstacionamientoDTO;
import com.tuti.exception.Excepcion;
import com.tuti.presentacion.error.MensajeError;
import com.tuti.servicios.EstacionamientoServiceImpl;

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

    @PostMapping("/estacionar")
    public ResponseEntity<Object> estacionarVehiculo(@Valid @RequestBody EstacionamientoForm estacionamientoForm , BindingResult result) throws Exception {
    	if (result.hasErrors()) {
			
    		
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.formatearError(result));
			
		}
      
            estacionamientoService.estacionarVehiculo(estacionamientoForm.getPatente(), estacionamientoForm.getPassword());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/estacionar").buildAndExpand(estacionamientoForm.getPatente())
    				.toUri(); 

    		return ResponseEntity.created(location).build();
      
    }
    /**
  	 * Permite liberar un vehiculo estacionado
  	 * @param patente
  	 * @param dni
  	 * @return
     * @throws Exception 
  	 * @throws Excepcion
  	 */
    @PutMapping("/liberar")
    public ResponseEntity<Object> liberarVehiculo(@Valid @RequestBody EstacionamientoForm estacionamientoForm , BindingResult result) throws Exception {
    	
    	if (result.hasErrors()) {
			
    		
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.formatearError(result));
			
		}
            estacionamientoService.liberarVehiculo(estacionamientoForm.getPatente(), estacionamientoForm.getPassword());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/liberar").buildAndExpand(estacionamientoForm.getPatente())
    				.toUri(); 

            return ResponseEntity.created(location).build();
       
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
}
