package com.proyecto.farmacia.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="roles")
public class Rol {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idRol;
	private String nombre;
	
	@OneToMany(mappedBy="rol")
	private List<Usuario> usuarios;
}
