package com.tuti.servicios;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tuti.accesoADatos.ComercioDAO;
import com.tuti.entidades.Comercio;
import com.tuti.exception.Excepcion;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class ComercioServiceImpl implements ComercioService {

    @Autowired
    private Validator validator;

    @Autowired
    private ComercioDAO comercioDAO;

    @Override
    public List<Comercio> getAll() {
        return comercioDAO.findAll();
    }

    @Override
    public void updateByCuit(Long cuit, Comercio comercio) throws Excepcion {
        // Validar que el comercio con el CUIT especificado exista
        Optional<Comercio> optionalComercio = comercioDAO.findComercioByCuit(cuit);
        if (!optionalComercio.isPresent()) {
            throw new Excepcion("Comercio no encontrado con el CUIT especificado: " + cuit, 0);
        }

        // Obtener el comercio existente
        Comercio comercioExistente = optionalComercio.get();

        // Actualizar solo los campos relevantes
        comercioExistente.setRazonSocial(comercio.getRazonSocial());
        comercioExistente.setDireccion(comercio.getDireccion());

        // No actualizar estado ni id aquí

        // Guardar los cambios
        comercioDAO.save(comercioExistente);
    }

    @Override
    public Optional<Comercio> getByCuit(Long cuit) {
        return comercioDAO.findComercioByCuit(cuit);
    }

    @Override
    public Optional<Comercio> getByRazonSocial(String razonSocial) {
        return comercioDAO.findComercioByRazonSocial(razonSocial);
    }

    @Override
    public void update(Comercio comercio) throws Excepcion {
        // Validar el comercio usando Bean Validation
        Set<ConstraintViolation<Comercio>> violations = validator.validate(comercio);
        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<Comercio> violation : violations) {
                errorMsg.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("\n");
            }
            throw new Excepcion(errorMsg.toString(), HttpStatus.BAD_REQUEST.value());
        }

        // Obtener el comercio actual desde la base de datos
        Optional<Comercio> existingComercio = comercioDAO.findById(comercio.getId());
        if (existingComercio.isPresent()) {
            // Actualizar solo los campos relevantes
            Comercio comercioExistente = existingComercio.get();
            comercioExistente.setRazonSocial(comercio.getRazonSocial());
            comercioExistente.setDireccion(comercio.getDireccion());

            // Mantener el estado como "autorizado"
            comercioExistente.setEstado("autorizado");

            // Guardar los cambios
            comercioDAO.save(comercioExistente);
        } else {
            throw new Excepcion("El comercio con ID " + comercio.getId() + " no existe.", HttpStatus.NOT_FOUND.value());
        }
    }


    @Override
    public void insert(Comercio comercio) throws Excepcion {
        // Validar el comercio usando Bean Validation
        Set<ConstraintViolation<Comercio>> violations = validator.validate(comercio);
        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<Comercio> violation : violations) {
                errorMsg.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("\n");
            }
            throw new Excepcion(errorMsg.toString(), HttpStatus.BAD_REQUEST.value());
        }

        // Verificar si ya existe un comercio con el mismo CUIT
        Optional<Comercio> existingComercio = comercioDAO.findComercioByCuit(comercio.getCuit());
        if (existingComercio.isPresent()) {
            throw new Excepcion("El comercio con el CUIT " + comercio.getCuit() + " ya existe.", HttpStatus.BAD_REQUEST.value());
        }

        // Establecer estado autorizado por defecto al insertar
        comercio.setEstado("autorizado");
        comercioDAO.save(comercio);
    }

    @Override
    public void suspend(Long cuit) throws Excepcion {
        // Buscar el comercio por CUIT
        Optional<Comercio> comercioOpt = comercioDAO.findComercioByCuit(cuit);
        if (comercioOpt.isPresent()) {
            Comercio comercio = comercioOpt.get();
            comercio.setEstado("suspendido");
            comercioDAO.save(comercio);
        } else {
            throw new Excepcion("No se encontró el comercio con CUIT " + cuit, HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    public void delete(Long id) {
        comercioDAO.deleteById(id);
    }

    @Override
    public List<Comercio> filtrar(Long cuit, String razonSocial) {
        if (cuit == null && razonSocial == null) {
            return comercioDAO.findAll();
        } else {
            return comercioDAO.findByRazonSocialOrCuit(razonSocial, cuit);
        }
    }
}


