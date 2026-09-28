package com.proyecto.farmacia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.farmacia.interfaceService.CategoriaService;
import com.proyecto.farmacia.model.Categoria;
import com.proyecto.farmacia.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Optional<Categoria> get(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

	@Override
	public List<Categoria> findAllActivas() {
		return categoriaRepository.findByEstadoTrue();
	}
}