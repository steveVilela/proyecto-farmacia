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
@Table(name = "detalle_compra")
@Data
@NoArgsConstructor
public class DetalleCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDetalleCompra")
	private Long idDetalleCompra;

	@Column(name = "idCompra")
	private Long idCompra;

	@Column(name = "idProducto")
	private Long idProducto;

	@Column(name = "cantidad")
	private int cantidad;

	@Column(name = "precioCompra")
	private double precioCompra;

	@Column(name = "subtotal")
	private double subtotal;

	@ManyToOne
	@JoinColumn(name = "idCompra", insertable = false, updatable = false)
	private Compra objCompra;

	@ManyToOne
	@JoinColumn(name = "idProducto", insertable = false, updatable = false)
	private Producto objProducto;

}
