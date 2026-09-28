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
@Table(name="detalle_venta")
@Data
@NoArgsConstructor
public class DetalleVenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "idDetalle")
	private Long idDetalle;
	
	@Column(name= "idVenta")
	private Long idVenta;
	
	@Column(name= "idProducto")
	private Long idProducto;
	
	@Column(name= "cantidad")
	private int cantidad;
	
	@Column(name= "precio")
	private double precio;
	
	@Column(name= "subtotal")
	private double subtotal;
	
	@ManyToOne
	@JoinColumn(name="idVenta",insertable = false, updatable = false)
	private Venta objVenta;
	
	@ManyToOne
	@JoinColumn(name= "idProducto", insertable = false , updatable = false)
	private Producto objProducto;
	
	
}
