package com.proyecto.farmacia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.farmacia.interfaceService.ProductoService;
import com.proyecto.farmacia.model.Producto;
import com.proyecto.farmacia.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public Producto save(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Optional<Producto> get(Long id) {
		return productoRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		productoRepository.deleteById(id);
		
	}

	@Override
	public List<Producto> findAll() {
		return productoRepository.findAll()	;
	}

	@Override
	public List<Producto> findAllActivas() {
		return productoRepository.findByEstadoTrue();
	}

}
