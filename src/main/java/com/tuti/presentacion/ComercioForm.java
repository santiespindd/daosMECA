package com.tuti.presentacion;

import com.tuti.entidades.Comercio;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ComercioForm {

    private Long id;

    @NotNull(message = "El CUIT no puede ser nulo")
    private Long cuit;

    @NotNull(message = "La razón social no puede ser nula")
    @Size(min = 2, max = 100, message = "La razón social debe tener entre 2 y 100 caracteres")
    private String razonSocial;

    @NotNull(message = "La dirección no puede ser nula")
    @Size(min = 2, max = 255, message = "La dirección debe tener entre 2 y 255 caracteres")
    private String direccion;

    @NotNull(message = "El estado no puede ser nulo")
    private String estado;

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

    public Comercio toPojo() {
        Comercio comercio = new Comercio();
      
        comercio.setCuit(this.cuit);
        comercio.setRazonSocial(this.razonSocial);
        comercio.setDireccion(this.direccion);
        comercio.setEstado(this.estado);
        return comercio;
    }
}

    


