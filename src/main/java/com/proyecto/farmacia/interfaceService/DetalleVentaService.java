package com.proyecto.farmacia.interfaceService;

import java.util.List;

import com.proyecto.farmacia.model.DetalleVenta;

public interface DetalleVentaService {

	public DetalleVenta save(DetalleVenta detalleVenta);
	public List<DetalleVenta> findAll();
	
}
