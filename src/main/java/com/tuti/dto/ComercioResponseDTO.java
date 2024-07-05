package com.tuti.dto;



import org.springframework.hateoas.RepresentationModel;
import com.tuti.entidades.Comercio;

/**
 * Objeto utilizado para construir la respuesta de los servicios
 */
public class ComercioResponseDTO extends RepresentationModel<ComercioResponseDTO> {

    private Long id;
    private Long cuit;
    private String razonSocial;
    private String direccion;
    private String estado;

    public ComercioResponseDTO(Comercio comercio) {
        this.id = comercio.getId();
        this.cuit = comercio.getCuit();
        this.razonSocial = comercio.getRazonSocial();
        this.direccion = comercio.getDireccion();
        this.estado = comercio.getEstado();
    }

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
        return id + " - " + cuit + " - " + razonSocial + " - " + direccion + " - " + estado;
    }
}

