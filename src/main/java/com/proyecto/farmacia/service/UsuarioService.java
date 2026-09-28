package com.proyecto.farmacia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.farmacia.interfaceService.IUsuarioService;
import com.proyecto.farmacia.model.Usuario;
import com.proyecto.farmacia.repository.UsuarioRepository;



@Service
public class UsuarioService implements IUsuarioService{
	
	@Autowired
	private UsuarioRepository repo;

	@Override
	public Usuario autenticar(String usuario, String password) {
		
		return repo.findByUsuarioAndPassword(usuario, password);
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		
		return repo.findAll();
	}

	@Override
	public void agregarUsuario(Usuario usuario) {
		repo.save(usuario);
		
	}

	@Override
	public Usuario ObtenerPorIdr(Integer id) {
		
		return repo.findById(id).orElse(null);
	}

	@Override
	public void actualizarUsuario(Usuario usuario) {
		repo.save(usuario);
		
	}

	@Override
	public void eliminarUsuario(Integer id) {
		repo.deleteById(id);
		
	}

	@Override
	public boolean existeUsuario(String usuario) {
		return repo.existsByUsuario(usuario);
	}

}
