package com.proyecto.farmacia.interfaceService;

import java.util.List;
import java.util.Optional;

import com.proyecto.farmacia.model.Proveedor;

public interface ProveedorService {

	public Proveedor save(Proveedor proveedor);
	public Optional<Proveedor> get(Long id);
	public void delete(Long id);
	public List<Proveedor> findAll();

}
