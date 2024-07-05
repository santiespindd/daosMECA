package com.tuti.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Comercio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El CUIT no puede ser nulo")
    @Digits(integer = 11, fraction = 0, message = "El CUIT debe ser un número positivo sin guiones")
    private Long cuit;
    
    @NotBlank(message = "La razón social no puede estar en blanco")
    @Size(max = 100, message = "La razón social no puede tener más de 100 caracteres")
    private String razonSocial;

    @Size(max = 200, message = "La dirección no puede tener más de 200 caracteres")
    private String direccion;

    private String estado = "autorizado"; // Estado por defecto

    // Constructor vacío
    public Comercio() {
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Comercio [id=" + id + ", cuit=" + cuit + ", razonSocial=" + razonSocial + ", direccion=" + direccion
                + ", estado=" + estado + "]";
    }
}


