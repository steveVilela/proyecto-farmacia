package com.proyecto.farmacia.interfaceService;

import java.util.List;

import com.proyecto.farmacia.model.Rol;


public interface IRolService {
	 public List<Rol> obtenerRoles();
	 public Rol obtenerPorId(Integer id);
}
