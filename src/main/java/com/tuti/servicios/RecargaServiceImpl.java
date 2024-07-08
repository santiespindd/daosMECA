package com.tuti.servicios;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuti.accesoADatos.RecargaDao;
import com.tuti.entidades.Comercio;
import com.tuti.entidades.Recarga;
import com.tuti.entidades.Usuario;
import com.tuti.exception.Excepcion;

import jakarta.transaction.Transactional;

@Service
public class RecargaServiceImpl implements RecargaService{
	
	@Autowired
	private RecargaDao recargaDao;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ComercioService comercioService;

	@Override
	public List<Recarga> getAllRecargas(){
		return recargaDao.findAll();
	}
	
	@Override
	public List<Recarga>getRecargaByPatente(String patente){
		return recargaDao.findByPatente(patente);
	}
	
	@Override
	public List<Recarga>getRecargaByUsuarioDni(Long dni){
		return recargaDao.findByUsuarioDni(dni);
	}
	
	@Override
	public List<Recarga>getRecargaByComercioCuit(Long comercioCuit){
		return recargaDao.findByComercioCuit(comercioCuit);
	}
	
	@Override
	@Transactional
	public Recarga realizarRecarga(String patente, Long comercioCuit, BigDecimal importe) throws Excepcion{
		Usuario usuario = usuarioService.getByPatente(patente)
				.orElseThrow(() -> new Excepcion("Patente no encontrada", 404));
		
		Comercio comercio = comercioService.getByCuit(comercioCuit)
				.orElseThrow(() -> new Excepcion("Comercio no encontrado", 404));
		
		if (!"autorizado".equals(comercio.getEstado())) {
			throw new Excepcion("El comercio no esta autorizado para realizar recargas", 400);
		}
		
		Recarga recarga = new Recarga();
		recarga.setUsuario(usuario);
		recarga.setComercio(comercio);
		recarga.setPatente(patente);
		recarga.setImporte(importe);
		
		usuario.setSaldo(usuario.getSaldo().add(importe));
		usuarioService.update(usuario);
		
		return recargaDao.save(recarga);
		}
	}
	
	

