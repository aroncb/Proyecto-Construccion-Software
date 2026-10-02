package com.example.demo.login;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.usuario.Usuario;
import com.example.demo.usuario.UsuarioService;

// Controller: I01 / F01 - Inicio de sesión del administrador y los motorizados
@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute(Sesion.USUARIO);
        if (usuario != null) {
            return redirigirSegunRol(usuario);
        }
        return "login";
    }

    @PostMapping("/login")
    public String ingresar(@RequestParam("username") String username,
                           @RequestParam("clave") String clave,
                           HttpSession session,
                           Model model) {
        Usuario usuario = usuarioService.autenticar(username, clave);
        if (usuario == null) {
            model.addAttribute("error", "Usuario o clave incorrectos, o el usuario está deshabilitado.");
            model.addAttribute("username", username);
            return "login";
        }
        session.setAttribute(Sesion.USUARIO, usuario);
        return redirigirSegunRol(usuario);
    }

    @GetMapping("/logout")
    public String salir(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // Cada rol entra a su propia pantalla de inicio
    private String redirigirSegunRol(Usuario usuario) {
        if ("MOTORIZADO".equals(usuario.getRol())) {
            return "redirect:/motorizado/pedidos";
        }
        return "redirect:/admin";
    }
}
