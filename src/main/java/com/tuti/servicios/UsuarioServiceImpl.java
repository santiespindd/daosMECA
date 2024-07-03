package com.tuti.servicios;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuti.accesoADatos.UsuarioDao;
import com.tuti.entidades.Usuario;
import com.tuti.exception.Excepcion;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
@Service
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	private  Validator validator;
	
	@Autowired
	private UsuarioDao dao;
	@Override
	public List<Usuario> getAll() {
		return dao.findAll();
	}
	@Override
	public Optional<Usuario> getById(Long id) {
		return  dao.findById(id);
		
	}
	@Override
	public void update(Usuario u) {
		dao.save(u);
	}
	@Override
	public void insert(Usuario u) throws Exception {
		
		Set<ConstraintViolation<Usuario>> cv = validator.validate(u);
		if(cv.size()>0)
		{
			String err="";
			for (ConstraintViolation<Usuario> constraintViolation : cv) {
				err+=constraintViolation.getPropertyPath()+": "+constraintViolation.getMessage()+"\n";
			}
			throw new Excepcion(err,400);
		}
		else if(getById(u.getDni()).isPresent())
		{
			throw new Excepcion("Ya existe una persona con ese dni.",400);
		}
		else
			dao.save(u);
	}
	@Override
	public void delete(Long id) {
		dao.deleteById(id);
	}
	@Override
	public List<Usuario> filtrar(String apellido, String nombre) {
		if(apellido==null && nombre==null)
			return dao.findAll();
		else
			return dao.findByApellidoOrNombre(apellido, nombre);
	}

}
