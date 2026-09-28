package com.proyecto.farmacia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.farmacia.interfaceService.ProveedorService;
import com.proyecto.farmacia.model.Proveedor;
import com.proyecto.farmacia.repository.ProveedorRepository;

@Service
public class ProveedorServiceImpl implements ProveedorService {

	@Autowired
	private ProveedorRepository proveedorRepository;

	@Override
	public Proveedor save(Proveedor proveedor) {
		return proveedorRepository.save(proveedor);
	}

	@Override
	public Optional<Proveedor> get(Long id) {
		return proveedorRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		proveedorRepository.deleteById(id);
	}

	@Override
	public List<Proveedor> findAll() {
		return proveedorRepository.findAll();
	}

}
