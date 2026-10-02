package com.example.demo.usuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Controller: recibe los request de las interfaces de usuarios
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // I12 / F12 - Ver usuarios
    @GetMapping("/list")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        return "usuario/lista";
    }

    // I11 / F11 - Registrar motorizado (formulario)
    @GetMapping("/nuevo")
    public String nuevo() {
        return "usuario/nuevo";
    }

    // I11 / F11 - Registrar motorizado (guarda o devuelve el error al formulario)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario, Model model) {
        String error = usuarioService.registrarMotorizado(usuario);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("usuario", usuario);
            return "usuario/nuevo";
        }
        return "redirect:/usuario/list";
    }

    // I13 / F13 - Actualizar usuario (formulario con datos)
    @GetMapping("/editar")
    public String editar(@RequestParam("id") int id, Model model) {
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "usuario/editar";
    }

    // I13 / F13 - Actualizar usuario (guarda o devuelve el error al formulario)
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Usuario usuario, Model model) {
        String error = usuarioService.actualizar(usuario);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("usuario", usuario);
            return "usuario/editar";
        }
        return "redirect:/usuario/list";
    }

    // I14 / F14 - Deshabilitar usuario (confirmación)
    @GetMapping("/estado")
    public String confirmarEstado(@RequestParam("id") int id, Model model) {
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "usuario/deshabilitar";
    }

    // I14 / F14 - Deshabilitar o habilitar usuario
    @PostMapping("/estado")
    public String cambiarEstado(@RequestParam("id") int id,
                                @RequestParam("activo") boolean activo,
                                Model model) {
        String error = usuarioService.cambiarEstado(id, activo);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("usuario", usuarioService.obtenerPorId(id));
            return "usuario/deshabilitar";
        }
        return "redirect:/usuario/list";
    }
}
