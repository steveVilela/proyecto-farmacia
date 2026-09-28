package com.proyecto.farmacia.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.farmacia.dto.DetalleVentaDTO;
import com.proyecto.farmacia.interfaceService.VentaService;
import com.proyecto.farmacia.model.DetalleVenta;
import com.proyecto.farmacia.model.Producto;
import com.proyecto.farmacia.model.Venta;
import com.proyecto.farmacia.repository.DetalleVentaRepository;
import com.proyecto.farmacia.repository.ProductoRepository;
import com.proyecto.farmacia.repository.VentaRepository;

@Service
public class VentaServiceImpl implements VentaService{

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;
	
	@Override
	public Venta save(Venta venta) {
		return ventaRepository.save(venta);
	}

	@Override
	public Optional<Venta> get(Long id) {
		return ventaRepository.findById(id);
	}

	@Override
	public List<Venta> findAll() {
		return ventaRepository.findAll();
	}
	
	@Transactional
	@Override
	public Venta registrarVenta(Long idUsuario, List<DetalleVentaDTO> carrito) {
		   if (carrito == null || carrito.isEmpty()) {
	            throw new RuntimeException("No hay productos en la venta");
	        }

	        double total = 0;

	        // Primero validamos productos y calculamos total
	        for (DetalleVentaDTO item : carrito) {

	            Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);

	            if (producto == null) {
	                throw new RuntimeException("Producto no encontrado");
	            }

	            if (!producto.getEstado()) {
	                throw new RuntimeException("El producto está inactivo: " + producto.getNombreMedicamento());
	            }

	            if (producto.getStock() < item.getCantidad()) {
	                throw new RuntimeException("Stock insuficiente para: " + producto.getNombreMedicamento());
	            }

	            double subtotal = producto.getPrecio() * item.getCantidad();
	            total += subtotal;
	        }

	        // Guardamos la cabecera de la venta
	        Venta venta = new Venta();
	        venta.setIdUsuario(idUsuario);
	        venta.setFechaVenta(new Date());
	        venta.setTotal(total);

	        Venta ventaGuardada = ventaRepository.save(venta);

	        // Guardamos los detalles y actualizamos stock
	        for (DetalleVentaDTO item : carrito) {

	            Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);

	            double precio = producto.getPrecio();
	            double subtotal = precio * item.getCantidad();

	            DetalleVenta detalle = new DetalleVenta();
	            detalle.setIdVenta(ventaGuardada.getIdVenta());
	            detalle.setIdProducto(producto.getIdProducto());
	            detalle.setCantidad(item.getCantidad());
	            detalle.setPrecio(precio);
	            detalle.setSubtotal(subtotal);

	            detalleVentaRepository.save(detalle);

	            producto.setStock(producto.getStock() - item.getCantidad());
	            productoRepository.save(producto);
	        }

	        return ventaGuardada;
	}

}
