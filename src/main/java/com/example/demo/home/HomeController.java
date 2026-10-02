package com.example.demo.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controller de inicio
@Controller
public class HomeController {

    // La página principal del sitio es el catálogo del cliente
    @GetMapping("/")
    public String inicio() {
        return "redirect:/catalogo";
    }

    // Panel del administrador
    @GetMapping("/admin")
    public String panelAdmin() {
        return "home";
    }
}
