package com.proyecto.farmacia.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.farmacia.interfaceService.CategoriaService;
import com.proyecto.farmacia.model.Categoria;
import com.proyecto.farmacia.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarCategorias(Model model, HttpSession session) {

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }
    	
        Categoria categoria = new Categoria();
        categoria.setEstado(true);

        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("categoria", categoria);
        model.addAttribute("modoEditar", false);
        
        return "guiCategoria";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria, HttpSession session) {
    	
    	Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }
        
        categoriaService.save(categoria);

        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable Long id, HttpSession session, Model model) {
    	
    	Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }
    	
        Optional<Categoria> categoriaOptional = categoriaService.get(id);

        if (categoriaOptional.isEmpty()) {
            return "redirect:/categorias";
        }

        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("categoria", categoriaOptional.get());
        model.addAttribute("modoEditar", true);

        return "guiCategoria";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id,HttpSession session) {
    	
    	Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

        categoriaService.delete(id);

        return "redirect:/categorias";
    }
}