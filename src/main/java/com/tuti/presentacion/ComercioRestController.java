package com.tuti.presentacion;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.tuti.entidades.Comercio;
import com.tuti.servicios.ComercioService;

import jakarta.validation.Valid;

import com.tuti.exception.Excepcion;

@RestController
@RequestMapping("/comercios")
public class ComercioRestController {

    @Autowired
    private ComercioService comercioService;

    @GetMapping
    public ResponseEntity<List<Comercio>> getAll() {
        List<Comercio> comercios = comercioService.getAll();
        return new ResponseEntity<>(comercios, HttpStatus.OK);
    }

    @GetMapping("/cuit/{cuit}")
    public ResponseEntity<Comercio> getByCuit(@PathVariable Long cuit) {
        Optional<Comercio> comercio = comercioService.getByCuit(cuit);
        return comercio.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/razonSocial/{razonSocial}")
    public ResponseEntity<Comercio> getByRazonSocial(@PathVariable String razonSocial) {
        Optional<Comercio> comercio = comercioService.getByRazonSocial(razonSocial);
        return comercio.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> insertComercio(@Valid @RequestBody ComercioForm comercioForm, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Error de validación en el formulario.");
        }

        try {
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComercio(@PathVariable Long id, @Valid @RequestBody ComercioForm comercioForm, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Error de validación en el formulario.");
        }

        try {
            Comercio comercio = comercioForm.toPojo();
            comercio.setId(id);
            comercioService.update(comercio);
            return ResponseEntity.ok(comercio);
        } catch (Excepcion e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> suspendComercio(@PathVariable Long id) {
        try {
            comercioService.suspend(id);
            return ResponseEntity.noContent().build();
        } catch (Excepcion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Comercio>> buscarComercios(@RequestParam(required = false) Long cuit,
                                                          @RequestParam(required = false) String razonSocial) {
        List<Comercio> comercios = comercioService.filtrar(cuit, razonSocial);
        return ResponseEntity.ok(comercios);
    }
}


