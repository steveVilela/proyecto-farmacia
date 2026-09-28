package com.proyecto.farmacia.interfaceService;

import java.util.List;

import com.proyecto.farmacia.dto.DetalleCompraDTO;
import com.proyecto.farmacia.model.Compra;

public interface CompraService {

	public Compra save(Compra compra);
	public Compra findById(Long id);
	public List<Compra> findAll();
	public Compra registrarCompra(Long idUsuario, Long idProveedor, List<DetalleCompraDTO> carrito);
	public Compra recibirCompra(Long idCompra);

}
