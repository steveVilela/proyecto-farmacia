package com.proyecto.farmacia.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.farmacia.interfaceService.CategoriaService;
import com.proyecto.farmacia.interfaceService.ProductoService;
import com.proyecto.farmacia.model.Producto;
import com.proyecto.farmacia.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarProductos(@RequestParam(name = "page", defaultValue = "0") int page,
                                  Model model, HttpSession session) {
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

        List<Producto> todosLosProductos = productoService.findAll();
        int pageSize = 10;
        int totalProductos = todosLosProductos.size();
        int totalPages = (int) Math.ceil((double) totalProductos / pageSize);
        if (totalPages == 0) totalPages = 1;

        if (page < 0) page = 0;
        if (page >= totalPages) page = totalPages - 1;

        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalProductos);

        List<Producto> paginaProductos;
        if (fromIndex <= toIndex && fromIndex < totalProductos) {
            paginaProductos = todosLosProductos.subList(fromIndex, toIndex);
        } else {
            paginaProductos = Collections.emptyList();
        }

        Producto producto = new Producto();
        producto.setEstado(true);

        model.addAttribute("productos", paginaProductos);
        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("categorias", categoriaService.findAllActivas());
        model.addAttribute("producto", producto);
        model.addAttribute("modoEditar", false);

        return "guiProducto";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto, HttpSession session) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }
        // Bloqueo: el vendedor no puede guardar ni editar
        if (usuarioLogueado.getRol() == null || usuarioLogueado.getRol().getIdRol() != 1) {
            return "redirect:/productos";
        }
        
        productoService.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Long id,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }
        // Bloqueo: el vendedor no puede editar
        if (usuarioLogueado.getRol() == null || usuarioLogueado.getRol().getIdRol() != 1) {
            return "redirect:/productos";
        }
        
        Optional<Producto> productoOptional = productoService.get(id);
        if (productoOptional.isEmpty()) {
            return "redirect:/productos";
        }

        List<Producto> todosLosProductos = productoService.findAll();
        int pageSize = 10;
        int totalProductos = todosLosProductos.size();
        int totalPages = (int) Math.ceil((double) totalProductos / pageSize);
        if (totalPages == 0) totalPages = 1;

        int fromIndex = Math.min(page * pageSize, totalProductos);
        int toIndex = Math.min(fromIndex + pageSize, totalProductos);
        List<Producto> paginaProductos = todosLosProductos.subList(fromIndex, toIndex);

        model.addAttribute("productos", paginaProductos);
        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("categorias", categoriaService.findAllActivas());
        model.addAttribute("producto", productoOptional.get());
        model.addAttribute("modoEditar", true);

        return "guiProducto";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, HttpSession session) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }
        // Bloqueo: el vendedor no puede eliminar
        if (usuarioLogueado.getRol() == null || usuarioLogueado.getRol().getIdRol() != 1) {
            return "redirect:/productos";
        }
        
        productoService.delete(id);
        return "redirect:/productos";
    }
}