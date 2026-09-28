package com.proyecto.farmacia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.proyecto.farmacia.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class InicioController {

    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

        return "inicio";
    }
}