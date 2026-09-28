package com.proyecto.farmacia.model;

import java.util.Date;

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
@Table(name = "compras")
@Data
@NoArgsConstructor
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCompra")
	private Long idCompra;

	@Column(name = "idProveedor")
	private Long idProveedor;

	@Column(name = "idUsuario")
	private Long idUsuario;

	@Column(name = "fechaCompra")
	private Date fechaCompra;

	@Column(name = "fechaEntrega")
	private Date fechaEntrega;

	@Column(name = "total")
	private double total;

	@Column(name = "estado")
	private String estado;

	@Column(name = "observacion")
	private String observacion;

	@ManyToOne
	@JoinColumn(name = "idProveedor", insertable = false, updatable = false)
	private Proveedor objProveedor;

	@ManyToOne
	@JoinColumn(name = "idUsuario", insertable = false, updatable = false)
	private Usuario objUsuario;

}
