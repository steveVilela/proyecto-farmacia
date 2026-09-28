package com.proyecto.farmacia.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.farmacia.dto.DetalleCompraDTO;
import com.proyecto.farmacia.interfaceService.CompraService;
import com.proyecto.farmacia.model.Compra;
import com.proyecto.farmacia.model.DetalleCompra;
import com.proyecto.farmacia.model.Producto;
import com.proyecto.farmacia.repository.CompraRepository;
import com.proyecto.farmacia.repository.DetalleCompraRepository;
import com.proyecto.farmacia.repository.ProductoRepository;

@Service
public class CompraServiceImpl implements CompraService {

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private DetalleCompraRepository detalleCompraRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public Compra save(Compra compra) {
		return compraRepository.save(compra);
	}

	@Override
	public Compra findById(Long id) {
		return compraRepository.findById(id).orElse(null);
	}

	@Override
	public List<Compra> findAll() {
		return compraRepository.findAll();
	}

	@Transactional
	@Override
	public Compra registrarCompra(Long idUsuario, Long idProveedor, List<DetalleCompraDTO> carrito) {

		if (carrito == null || carrito.isEmpty()) {
			throw new RuntimeException("No hay productos en la compra");
		}

		double total = 0;

		for (DetalleCompraDTO item : carrito) {

			Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);

			if (producto == null) {
				throw new RuntimeException("Producto no encontrado");
			}

			double subtotal = item.getPrecioCompra() * item.getCantidad();
			total += subtotal;
		}

		Compra compra = new Compra();
		compra.setIdProveedor(idProveedor);
		compra.setIdUsuario(idUsuario);
		compra.setFechaCompra(new Date());
		compra.setFechaEntrega(null);
		compra.setTotal(total);
		compra.setEstado("PENDIENTE");

		Compra compraGuardada = compraRepository.save(compra);

		for (DetalleCompraDTO item : carrito) {

			Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);

			double precioCompra = item.getPrecioCompra();
			double subtotal = precioCompra * item.getCantidad();

			DetalleCompra detalle = new DetalleCompra();
			detalle.setIdCompra(compraGuardada.getIdCompra());
			detalle.setIdProducto(producto.getIdProducto());
			detalle.setCantidad(item.getCantidad());
			detalle.setPrecioCompra(precioCompra);
			detalle.setSubtotal(subtotal);

			detalleCompraRepository.save(detalle);
		}

		return compraGuardada;
	}

	@Transactional
	@Override
	public Compra recibirCompra(Long idCompra) {

		Compra compra = compraRepository.findById(idCompra).orElse(null);

		if (compra == null) {
			throw new RuntimeException("Compra no encontrada");
		}

		if ("RECIBIDA".equals(compra.getEstado())) {
			throw new RuntimeException("La compra ya fue recibida");
		}

		compra.setFechaEntrega(new Date());
		compra.setEstado("RECIBIDA");
		compraRepository.save(compra);

		List<DetalleCompra> detalles = detalleCompraRepository.findByIdCompra(idCompra);

		for (DetalleCompra detalle : detalles) {

			Producto producto = productoRepository.findById(detalle.getIdProducto()).orElse(null);

			if (producto != null) {
				producto.setStock(producto.getStock() + detalle.getCantidad());
				productoRepository.save(producto);
			}
		}

		return compra;
	}

}
