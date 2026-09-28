package com.proyecto.farmacia.interfaceService;

import java.util.List;

import com.proyecto.farmacia.model.Usuario;



public interface IUsuarioService {
	
	public Usuario autenticar(String usuario,String password);
	public List<Usuario> obtenerUsuarios();
	public void agregarUsuario(Usuario usuario);
	public Usuario ObtenerPorIdr(Integer id);
	public void actualizarUsuario(Usuario usuario);
	public void eliminarUsuario(Integer id);
	public boolean existeUsuario(String usuario);
	
}
