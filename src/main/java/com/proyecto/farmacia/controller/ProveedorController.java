package com.proyecto.farmacia.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.farmacia.interfaceService.ProveedorService;
import com.proyecto.farmacia.model.Proveedor;
import com.proyecto.farmacia.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProveedorController {

	@Autowired
	private ProveedorService proveedorService;

	@GetMapping("/proveedores")
	public String listar(Model model) {

		model.addAttribute("proveedores", proveedorService.findAll());
		model.addAttribute("proveedor", new Proveedor());
		model.addAttribute("modoEditar", false);

		return "guiProveedor";
	}

	@GetMapping("/proveedores/editar/{id}")
	public String editar(@PathVariable Long id, Model model, HttpSession session) {
		
		Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

		Optional<Proveedor> proveedorOptional = proveedorService.get(id);

		if (proveedorOptional.isEmpty()) {
			return "redirect:/proveedores";
		}

		model.addAttribute("proveedores", proveedorService.findAll());
		model.addAttribute("proveedor", proveedorOptional.get());
		model.addAttribute("modoEditar", true);

		return "guiProveedor";
	}

	@PostMapping("/proveedores/guardar")
	public String guardar(Proveedor proveedor, HttpSession session) {
		
		Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

		proveedorService.save(proveedor);

		return "redirect:/proveedores";
	}

	@GetMapping("/proveedores/eliminar/{id}")
	public String eliminar(@PathVariable Long id, HttpSession session) {
		
		Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

		proveedorService.delete(id);

		return "redirect:/proveedores";
	}

}
