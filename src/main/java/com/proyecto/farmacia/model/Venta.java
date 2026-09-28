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
@Table(name= "ventas")
@Data
@NoArgsConstructor
public class Venta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idVenta")
	private Long idVenta;
	
	@Column(name="idUsuario")
	private Long idUsuario;
	
	@Column(name="fechaVenta")
	private Date fechaVenta;
	
	@Column(name="total")
	private double total;
	
	@ManyToOne
	@JoinColumn(name = "idUsuario",insertable = false , updatable = false)
	private Usuario objUsuario;

	public Venta(Long idVenta, Long idUsuario, Date fechaVenta, double total) {
		super();
		this.idVenta = idVenta;
		this.idUsuario = idUsuario;
		this.fechaVenta = fechaVenta;
		this.total = total;
	}
	
	
	

}
