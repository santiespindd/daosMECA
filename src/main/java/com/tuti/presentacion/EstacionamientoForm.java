package com.tuti.presentacion;

import com.tuti.entidades.Estacionamiento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EstacionamientoForm {
	 @NotNull(message = "patente no debe ser nulo")
    @Size(min = 1, max = 10)
    private String patente;

	 @NotNull(message = "password no debe ser nulo")
    @Size(min = 1, max = 20)
    private String password;

    // Getters y Setters
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
 

    
}
