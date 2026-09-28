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

import com.proyecto.farmacia.dto.DetalleVentaDTO;
import com.proyecto.farmacia.interfaceService.ProductoService;
import com.proyecto.farmacia.interfaceService.VentaService;
import com.proyecto.farmacia.model.Producto;
import com.proyecto.farmacia.model.Usuario;

@Controller

public class VentaController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    @GetMapping("/ventas")
    public String mostrarVenta(Model model, HttpSession session) {

    	Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }
        
        List<DetalleVentaDTO> carrito = obtenerCarrito(session);

        model.addAttribute("productos", productoService.findAllActivas());
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", calcularTotal(carrito));
        model.addAttribute("ventas", ventaService.findAll());
        
        return "guiVenta";
    }

    @PostMapping("/ventas/agregar")
    public String agregarProducto(@RequestParam("idProducto") Long idProducto,
                                  @RequestParam("cantidad") int cantidad,
                                  HttpSession session) {
    	
    	Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

        if (cantidad <= 0) {
            return "redirect:/ventas";
        }

        Optional<Producto> productoOptional = productoService.get(idProducto);

        if (productoOptional.isEmpty()) {
            return "redirect:/ventas";
        }

        Producto producto = productoOptional.get();

        if (!producto.getEstado()) {
            return "redirect:/ventas";
        }

        if (producto.getStock() < cantidad) {
            return "redirect:/ventas";
        }

        List<DetalleVentaDTO> carrito = obtenerCarrito(session);

        boolean productoExiste = false;

        for (DetalleVentaDTO item : carrito) {
            if (item.getIdProducto().equals(idProducto)) {

                int nuevaCantidad = item.getCantidad() + cantidad;

                if (producto.getStock() >= nuevaCantidad) {
                    item.setCantidad(nuevaCantidad);
                    item.setSubtotal(item.getPrecio() * nuevaCantidad);
                }

                productoExiste = true;
                break;
            }
        }

        if (!productoExiste) {
            DetalleVentaDTO detalle = new DetalleVentaDTO();

            detalle.setIdProducto(producto.getIdProducto());
            detalle.setNombreMedicamento(producto.getNombreMedicamento());
            detalle.setCantidad(cantidad);
            detalle.setPrecio(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio() * cantidad);

            carrito.add(detalle);
        }

        session.setAttribute("carrito", carrito);

        return "redirect:/ventas";
    }

    @GetMapping("/ventas/quitar/{idProducto}")
    public String quitarProducto(@PathVariable Long idProducto, HttpSession session) {
    	
    	Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

        List<DetalleVentaDTO> carrito = obtenerCarrito(session);

        carrito.removeIf(item -> item.getIdProducto().equals(idProducto));

        session.setAttribute("carrito", carrito);

        return "redirect:/ventas";
    }

    @GetMapping("/ventas/limpiar")
    public String limpiarCarrito(HttpSession session) {
    	
    	Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

        session.removeAttribute("carrito");

        return "redirect:/ventas";
    }

    @PostMapping("/ventas/guardar")
    public String guardarVenta(HttpSession session) {
    	
    	Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

        List<DetalleVentaDTO> carrito = obtenerCarrito(session);

        if (carrito.isEmpty()) {
            return "redirect:/ventas";
        }

        Long idUsuario = obtenerIdUsuario(session);

        ventaService.registrarVenta(idUsuario, carrito);

        session.removeAttribute("carrito");

        return "redirect:/ventas";
    }


    private List<DetalleVentaDTO> obtenerCarrito(HttpSession session) {
    	

        List<DetalleVentaDTO> carrito = (List<DetalleVentaDTO>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }

        return carrito;
    }

    private double calcularTotal(List<DetalleVentaDTO> carrito) {

        double total = 0;

        for (DetalleVentaDTO item : carrito) {
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