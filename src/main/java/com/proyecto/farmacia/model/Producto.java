package com.proyecto.farmacia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos" )
@Data
@NoArgsConstructor
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idProducto")
	private Long idProducto;
	
	@Column(name="nombreMedicamento")
	private String nombreMedicamento;
	
	@Column(name="idCategoria")
	private Long idCategoria;
	
	@Column(name="precio")
	private double precio;
	
	@Column(name="stock")
	private int stock;
	
	@Column(name="estado")
	private Boolean estado;
	
	@ManyToOne
	@JoinColumn(name="idCategoria",insertable = false, updatable = false)
	private Categoria objCategoria;

	public Producto(Long idProducto, String nombreMedicamento, Long idCategoria, double precio, int stock,
			Boolean estado) {
		super();
		this.idProducto = idProducto;
		this.nombreMedicamento = nombreMedicamento;
		this.idCategoria = idCategoria;
		this.precio = precio;
		this.stock = stock;
		this.estado = estado;
	}
	
	
	
	
	
	
	
}
