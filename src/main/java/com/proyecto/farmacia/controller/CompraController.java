package com.proyecto.farmacia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.farmacia.dto.DetalleCompraDTO;
import com.proyecto.farmacia.interfaceService.CompraService;
import com.proyecto.farmacia.interfaceService.ProductoService;
import com.proyecto.farmacia.interfaceService.ProveedorService;
import com.proyecto.farmacia.model.Producto;
import com.proyecto.farmacia.model.Usuario;

@Controller
public class CompraController {

	@Autowired
	private ProductoService productoService;

	@Autowired
	private ProveedorService proveedorService;

	@Autowired
	private CompraService compraService;

	@GetMapping("/compras")
	public String mostrarCompra(Model model, HttpSession session) {

		Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }
		
		List<DetalleCompraDTO> carrito = obtenerCarrito(session);

		model.addAttribute("productos", productoService.findAll());
		model.addAttribute("proveedores", proveedorService.findAll());
		model.addAttribute("carrito", carrito);
		model.addAttribute("total", calcularTotal(carrito));
		model.addAttribute("compras", compraService.findAll());

		return "guiCompra";
	}

	@PostMapping("/compras/agregar")
	public String agregarProducto(@RequestParam("idProducto") Long idProducto,
	                              @RequestParam("cantidad") int cantidad,
	                              @RequestParam("precioCompra") double precioCompra,
	                              HttpSession session) {
		
		Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

		if (cantidad <= 0 || precioCompra <= 0) {
			return "redirect:/compras";
		}

		Optional<Producto> productoOptional = productoService.get(idProducto);

		if (productoOptional.isEmpty()) {
			return "redirect:/compras";
		}

		Producto producto = productoOptional.get();

		if (!producto.getEstado()) {
			return "redirect:/compras";
		}

		List<DetalleCompraDTO> carrito = obtenerCarrito(session);

		boolean productoExiste = false;

		for (DetalleCompraDTO item : carrito) {
			if (item.getIdProducto().equals(idProducto)) {

				int nuevaCantidad = item.getCantidad() + cantidad;
				item.setCantidad(nuevaCantidad);
				item.setPrecioCompra(precioCompra);
				item.setSubtotal(precioCompra * nuevaCantidad);

				productoExiste = true;
				break;
			}
		}

		if (!productoExiste) {
			DetalleCompraDTO detalle = new DetalleCompraDTO();

			detalle.setIdProducto(producto.getIdProducto());
			detalle.setNombreMedicamento(producto.getNombreMedicamento());
			detalle.setCantidad(cantidad);
			detalle.setPrecioCompra(precioCompra);
			detalle.setSubtotal(precioCompra * cantidad);

			carrito.add(detalle);
		}

		session.setAttribute("carritoCompra", carrito);

		return "redirect:/compras";
	}

	@GetMapping("/compras/quitar/{idProducto}")
	public String quitarProducto(@PathVariable Long idProducto, HttpSession session) {

		Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }
        
		List<DetalleCompraDTO> carrito = obtenerCarrito(session);

		carrito.removeIf(item -> item.getIdProducto().equals(idProducto));

		session.setAttribute("carritoCompra", carrito);

		return "redirect:/compras";
	}

	@GetMapping("/compras/limpiar")
	public String limpiarCarrito(HttpSession session) {
		
		Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

		session.removeAttribute("carritoCompra");

		return "redirect:/compras";
	}

	@PostMapping("/compras/guardar")
	public String guardarCompra(@RequestParam("idProveedor") Long idProveedor,
	                            HttpSession session) {
		
		Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

		List<DetalleCompraDTO> carrito = obtenerCarrito(session);

		if (carrito.isEmpty()) {
			return "redirect:/compras";
		}

		Long idUsuario = obtenerIdUsuario(session);

		compraService.registrarCompra(idUsuario, idProveedor, carrito);

		session.removeAttribute("carritoCompra");

		return "redirect:/compras";
	}

	@PostMapping("/compras/recibir/{idCompra}")
	public String recibirCompra(@PathVariable Long idCompra) {

		compraService.recibirCompra(idCompra);

		return "redirect:/compras";
	}

	private List<DetalleCompraDTO> obtenerCarrito(HttpSession session) {

		
		
		List<DetalleCompraDTO> carrito = (List<DetalleCompraDTO>) session.getAttribute("carritoCompra");

		if (carrito == null) {
			carrito = new ArrayList<>();
			session.setAttribute("carritoCompra", carrito);
		}

		return carrito;
	}

	private double calcularTotal(List<DetalleCompraDTO> carrito) {

		double total = 0;

		for (DetalleCompraDTO item : carrito) {
			total += item.getSubtotal();
		}

		return total;
	}

	private Long obtenerIdUsuario(HttpSession session) {

		Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

		if (usuarioLogueado != null) {
			return usuarioLogueado.getIdUsuario();
		}

		return 1L;
	}

}
