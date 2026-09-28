package com.proyecto.farmacia.interfaceService;

import java.util.List;
import java.util.Optional;

import com.proyecto.farmacia.dto.DetalleVentaDTO;
import com.proyecto.farmacia.model.Venta;

public interface VentaService  {

	public Venta save(Venta venta);
	public Optional<Venta> get(Long id);
	public List<Venta> findAll();
	public Venta registrarVenta(Long idUsuario, List<DetalleVentaDTO> carrito);
	
}

