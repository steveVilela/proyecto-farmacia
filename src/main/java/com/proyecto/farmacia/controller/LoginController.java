package com.proyecto.farmacia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.farmacia.interfaceService.IUsuarioService;
import com.proyecto.farmacia.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/loginController")
public class LoginController {

	@Autowired
	private IUsuarioService usuarioService;
	
	//Equivale a login(request,response
	@GetMapping
	public String login() {
		return "login";
	}
	//Equivale a opcion autenticar
	@PostMapping("/autenticar")
	public String autenticar(@RequestParam("usuario") String usuario,
							@RequestParam("password") String password, HttpSession session, Model model) {
		Usuario u=usuarioService.autenticar(usuario, password);
		if(u==null) {
			model.addAttribute("error", "usuario y/o contraseña incorrectos");
			return "login";
		}
		session.setAttribute("usuarioLogueado", u);
		
		return "redirect:/inicio";
	}
	//equivale a opcion=logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return"redirect:/loginController";
	}
	
}
