package com.tuti.servicios;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuti.accesoADatos.EstacionamientoDAO;
import com.tuti.accesoADatos.UsuarioDao;
import com.tuti.dto.EstacionamientoDTO;
import com.tuti.entidades.Estacionamiento;
import com.tuti.entidades.Usuario;

@Service
public class EstacionamientoServiceImpl implements EstacionamientoService {

    @Autowired
    private EstacionamientoDAO estacionamientoDAO;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    public void estacionarVehiculo(String patente, String password) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioService.getByPatente(patente);
        
        if (!usuarioOpt.isPresent() || !usuarioOpt.get().getPassword().equals(password)) {
            throw new Exception("Usuario no encontrado o contraseña incorrecta");
        }
       
        	
        	 Usuario usuario = usuarioOpt.get();

             Optional<Estacionamiento> estacionamientoOpt = estacionamientoDAO.findByPatente(patente);
             if (estacionamientoOpt.isPresent()) {
                 Estacionamiento estacionamiento = estacionamientoOpt.get();
                 if ("Estacionado".equals(estacionamiento.getEstado())) {
                     throw new Exception("El vehículo ya está estacionado");
                 }
                 estacionamiento.setEstado("Estacionado");
                 estacionamientoDAO.save(estacionamiento);
                
             }

             Estacionamiento nuevoEstacionamiento = new Estacionamiento();
             nuevoEstacionamiento.setPatente(patente);
             nuevoEstacionamiento.setEstado("Estacionado");
             nuevoEstacionamiento.setUsuario(usuario);
             estacionamientoDAO.save(nuevoEstacionamiento);
      
       
        
    }

  /*  public void liberarVehiculo(String patente, String password) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioDAO.findByPassword(password); /// ESTO DEBERIA SER UN SERVICIO DE USUARIO
        if (!usuarioOpt.isPresent()) {
            throw new Exception("Usuario no encontrado o contraseña incorrecta");
        }

        Optional<Estacionamiento> estacionamientoOpt = estacionamientoDAO.findByPatente(patente);
        if (!estacionamientoOpt.isPresent()) {
            throw new Exception("El vehículo no está registrado");
        }

        Estacionamiento estacionamiento = estacionamientoOpt.get();
        if ("Libre".equals(estacionamiento.getEstado())) {
            throw new Exception("El vehículo ya está libre");
        }

        estacionamiento.setEstado("Libre");
        estacionamientoDAO.save(estacionamiento);
        
    }*/

    public EstacionamientoDTO consultarEstado(String patente) throws Exception {
        Optional<Estacionamiento> estacionamientoOpt = estacionamientoDAO.findByPatente(patente);
        if (!estacionamientoOpt.isPresent()) {
            throw new Exception("El vehículo no está registrado");
        }

        return convertToDTO(estacionamientoOpt.get());
    }

    private EstacionamientoDTO convertToDTO(Estacionamiento estacionamiento) {
        EstacionamientoDTO dto = new EstacionamientoDTO();
        dto.setPatente(estacionamiento.getPatente());
        dto.setEstado(estacionamiento.getEstado());
        dto.setUsuarioId(estacionamiento.getUsuario().getDni());
        return dto;
    }

	@Override
	public void liberarVehiculo(String patente, String password) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
