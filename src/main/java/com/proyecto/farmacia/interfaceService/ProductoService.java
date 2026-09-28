package com.proyecto.farmacia.interfaceService;

import java.util.List;
import java.util.Optional;

import com.proyecto.farmacia.model.Producto;

public interface ProductoService {

	public Producto save(Producto producto);
	public Optional<Producto> get(Long id);
	public void delete(Long id);
	public List<Producto> findAll();
	public List<Producto> findAllActivas();
	
}
