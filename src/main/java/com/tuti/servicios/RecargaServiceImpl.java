package com.tuti.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuti.accesoADatos.RecargaDao;
import com.tuti.entidades.Comercio;
import com.tuti.entidades.Estacionamiento;
import com.tuti.entidades.Recarga;
import com.tuti.entidades.Usuario;
import com.tuti.exception.Excepcion;

@Service
public class RecargaServiceImpl implements RecargaService{
	
	@Autowired
	private RecargaDao dao;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ComercioService comercioService;
	@Autowired
	private EstacionamientoService estacionamientoService;
	
	public List<Recarga>getAll(){
		return dao.findAll();
	}
	
	public void insert(Recarga recarga) throws Exception{
		Optional<Usuario> usuarioOpt = usuarioService.getById(recarga.getUsuario().getDni());
		Optional<Comercio>comercioOpt = comercioService.getByCuit(recarga.getComercio().getId());
	//	Optional<Estacionamiento>estacionamientoOpt = estacionamientoService.(recarga.getEstacionamiento().getPatente());
		
		if(usuarioOpt.isEmpty() || comercioOpt.isEmpty()) {
			throw new Excepcion("Usuario, Comercio o estacionamiento no encontrado", 400);
		}
		
		Usuario usuario = usuarioOpt.get();
		Comercio comercio = comercioOpt.get();
	//	Estacionamiento estacionamiento = estacionamientoOpt.get();
		
		usuario.setSaldo(usuario.getSaldo().add(recarga.getImporte()));
		usuarioService.update(usuario);
		recarga.setUsuario(usuario);
		recarga.setComercio(comercio);
	//	recarga.setEstacionamiento(estacionamiento);
		dao.save(recarga);		
	}
	public void delete(Long id) {
		dao.deleteById(id);
	}
	   @Override
	    public List<Recarga> getByUsuarioDni(Long dni) {
	        return dao.findByUsuarioDni(dni);
	    }
	    @Override
	    public List<Recarga> getByEstacionamientoPatente(String patente) {
	        return dao.findByEstacionamientoPatente(patente);
	    }

	@Override
	public Optional<Recarga> getById(Long id) {
		return dao.findById(id);
	}

	@Override
	public List<Recarga> getByComercioCuit(Long cuit) {

		return dao.findByComercioCuit(cuit);
	}

}
