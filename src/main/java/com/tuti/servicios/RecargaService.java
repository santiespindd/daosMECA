package com.tuti.servicios;

import java.util.List;
import java.util.Optional;
import com.tuti.entidades.Recarga;

public interface RecargaService {
	
	List<Recarga> getAll();
	Optional<Recarga>getById(Long id);
	void insert(Recarga recarga) throws Exception;
	void delete(Long id);
}
