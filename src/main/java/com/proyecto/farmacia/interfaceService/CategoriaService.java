package com.proyecto.farmacia.interfaceService;

import java.util.List;
import java.util.Optional;

import com.proyecto.farmacia.model.Categoria;

public interface CategoriaService {

	public Categoria save(Categoria categoria);

	public Optional<Categoria> get(Long id);

	public void delete(Long id);

	public List<Categoria> findAll();
    
	public List<Categoria> findAllActivas();
}