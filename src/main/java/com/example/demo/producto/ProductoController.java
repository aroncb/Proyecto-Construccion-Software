package com.example.demo.producto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.categoria.CategoriaService;

// Controller: recibe los request de las interfaces de producto
@Controller
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    // I08 / F08 - Consultar productos
    @GetMapping("/list")
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listar());
        return "producto/lista";
    }

    // I07 / F07 - Crear producto (formulario con categorías activas)
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("categorias", categoriaService.listarActivas());
        return "producto/nuevo";
    }

    // I07 / F07 - Crear producto (guarda)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        productoService.registrar(producto);
        return "redirect:/producto/list";
    }

    // I09 / F09 - Actualizar producto (formulario con datos)
    @GetMapping("/editar")
    public String editar(@RequestParam("id") int id, Model model) {
        model.addAttribute("producto", productoService.obtenerPorId(id));
        model.addAttribute("categorias", categoriaService.listar());
        return "producto/editar";
    }

    // I09 / F09 - Actualizar producto (guarda cambios)
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Producto producto) {
        productoService.actualizar(producto);
        return "redirect:/producto/list";
    }

    // I10 / F10 - Cambiar disponibilidad (muestra el formulario)
    @GetMapping("/disponibilidad")
    public String disponibilidad(@RequestParam("id") int id, Model model) {
        model.addAttribute("producto", productoService.obtenerPorId(id));
        return "producto/disponibilidad";
    }

    // I10 / F10 - Cambiar disponibilidad (guarda)
    @PostMapping("/disponibilidad")
    public String cambiarDisponibilidad(@RequestParam("id") int id,
                                        @RequestParam("disponible") boolean disponible) {
        productoService.cambiarDisponibilidad(id, disponible);
        return "redirect:/producto/list";
    }
}
