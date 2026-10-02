package com.example.demo.categoria;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Controller: recibe los request de las interfaces de categoría
@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // I04 / F04 - Consultar categorías
    @GetMapping("/list")
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listar());
        return "categoria/lista";
    }

    // I03 / F03 - Crear categoría (muestra el formulario)
    @GetMapping("/nuevo")
    public String nuevo() {
        return "categoria/nuevo";
    }

    // I03 / F03 - Crear categoría (guarda)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria) {
        categoriaService.registrar(categoria);
        return "redirect:/categoria/list";
    }

    // I05 / F05 - Actualizar categoría (muestra el formulario con datos)
    @GetMapping("/editar")
    public String editar(@RequestParam("id") int id, Model model) {
        model.addAttribute("categoria", categoriaService.obtenerPorId(id));
        return "categoria/editar";
    }

    // I05 / F05 - Actualizar categoría (guarda cambios)
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Categoria categoria) {
        categoriaService.actualizar(categoria);
        return "redirect:/categoria/list";
    }

    // I06 / F06 - Deshabilitar categoría (muestra la confirmación)
    @GetMapping("/estado")
    public String confirmarEstado(@RequestParam("id") int id, Model model) {
        model.addAttribute("categoria", categoriaService.obtenerPorId(id));
        return "categoria/deshabilitar";
    }

    // I06 / F06 - Deshabilitar o habilitar categoría
    @PostMapping("/estado")
    public String cambiarEstado(@RequestParam("id") int id, @RequestParam("activo") boolean activo) {
        categoriaService.cambiarEstado(id, activo);
        return "redirect:/categoria/list";
    }
}
