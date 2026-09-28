package com.proyecto.farmacia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.farmacia.interfaceService.IRolService;
import com.proyecto.farmacia.model.Rol;
import com.proyecto.farmacia.repository.RolRepository;


@Service
public class RolService implements IRolService {
	
	@Autowired
	private RolRepository repo;
	@Override
	public List<Rol> obtenerRoles() {
		
		return repo.findAll();
	}
	@Override
	public Rol obtenerPorId(Integer id) {
		return repo.findById(id).orElse(null);
		
	}

	
	
}
