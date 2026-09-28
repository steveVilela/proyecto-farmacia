package com.proyecto.farmacia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.farmacia.model.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
	
	Usuario findByUsuarioAndPassword(String usuario,String password);//esto remplaza a dao.autenticar(usuario,password)
	boolean existsByUsuario(String usuario);
}
