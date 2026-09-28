package com.proyecto.farmacia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.farmacia.interfaceService.IRolService;
import com.proyecto.farmacia.interfaceService.IUsuarioService;
import com.proyecto.farmacia.model.Rol;
import com.proyecto.farmacia.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;
    
    @Autowired
    private IRolService rolService;

    // Equivale a opcion = Lista
    @GetMapping
    public String listarUsuarios(Model model, HttpSession session) {
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }
        
        // Bloqueo para cualquier usuario que no sea Administrador (idRol != 1)
        if (usuarioLogueado.getRol() == null || usuarioLogueado.getRol().getIdRol() != 1) {
            return "redirect:/inicio";
        }
        
        List<Usuario> listaUsuarios = usuarioService.obtenerUsuarios();
        List<Rol> listaRoles = rolService.obtenerRoles();
        model.addAttribute("listaUsuarios", listaUsuarios);
        model.addAttribute("listaRoles", listaRoles);
        model.addAttribute("modoEditar", false);
        model.addAttribute("usuarioEditar", new Usuario());
        return "guiUsuario";
    }

    // Equivale a insertar
    @PostMapping("/insertar")
    public String registrarUsuario(@RequestParam("txtNombre") String nombre,
                                  @RequestParam("txtUsuario") String usuario,
                                  @RequestParam("txtPassword") String password,
                                  @RequestParam("cboRol") Integer idRol,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession session) {
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

        // Bloqueo para cualquier usuario que no sea Administrador (idRol != 1)
        if (usuarioLogueado.getRol() == null || usuarioLogueado.getRol().getIdRol() != 1) {
            return "redirect:/inicio";
        }
        
        if (usuarioService.existeUsuario(usuario)) {
            redirectAttributes.addFlashAttribute("error", 
                "El nombre de usuario ya existe. Por favor ingrese otro.");
            return "redirect:/usuarios";
        }
        
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setUsuario(usuario);
        u.setPassword(password);
        Rol rol = rolService.obtenerPorId(idRol);
        u.setRol(rol);
        usuarioService.agregarUsuario(u);
        return "redirect:/usuarios?msj=create";
    }

    // Equivale a editar
    @GetMapping("/editar/{id}")
    public String cargarUsuario(@PathVariable Integer id, Model model, HttpSession session) {
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

        // Bloqueo para cualquier usuario que no sea Administrador (idRol != 1)
        if (usuarioLogueado.getRol() == null || usuarioLogueado.getRol().getIdRol() != 1) {
            return "redirect:/inicio";
        }
        
        Usuario usuarioEditar = usuarioService.ObtenerPorIdr(id);
        List<Usuario> listaUsuarios = usuarioService.obtenerUsuarios();
        List<Rol> listaRoles = rolService.obtenerRoles();
        model.addAttribute("usuarioEditar", usuarioEditar);
        model.addAttribute("listaUsuarios", listaUsuarios);
        model.addAttribute("listaRoles", listaRoles);
        model.addAttribute("modoEditar", true);
        
        return "guiUsuario";
    }

    // Equivale a actualizar
    @PostMapping("/actualizar")
    public String actualizarUsuario(@RequestParam Long idUsuario,
                                   @RequestParam String txtNombre,
                                   @RequestParam String txtUsuario,
                                   @RequestParam String txtPassword,
                                   @RequestParam Integer cboRol,
                                   HttpSession session) {
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

        // Bloqueo para cualquier usuario que no sea Administrador (idRol != 1)
        if (usuarioLogueado.getRol() == null || usuarioLogueado.getRol().getIdRol() != 1) {
            return "redirect:/inicio";
        }

        Usuario u = new Usuario();
        u.setIdUsuario(idUsuario);
        u.setNombre(txtNombre);
        u.setUsuario(txtUsuario);
        u.setPassword(txtPassword);
        Rol rol = rolService.obtenerPorId(cboRol);
        u.setRol(rol);
        usuarioService.actualizarUsuario(u);
        return "redirect:/usuarios?msj=update";
    }

    // Equivale a eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, HttpSession session) {
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/loginController";
        }

        // Bloqueo para cualquier usuario que no sea Administrador (idRol != 1)
        if (usuarioLogueado.getRol() == null || usuarioLogueado.getRol().getIdRol() != 1) {
            return "redirect:/inicio";
        }

        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios?msj=delete";
    }
}