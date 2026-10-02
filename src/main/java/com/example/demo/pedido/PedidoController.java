package com.example.demo.pedido;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.producto.ProductoService;

// Controller: interfaces del cliente (catálogo, pedido y seguimiento)
@Controller
public class PedidoController {

    // Número de Yape del negocio: cámbialo por el número real
    private static final String NUMERO_YAPE = "987 654 321";

    private final PedidoService pedidoService;
    private final ProductoService productoService;

    public PedidoController(PedidoService pedidoService, ProductoService productoService) {
        this.pedidoService = pedidoService;
        this.productoService = productoService;
    }

    // I15 / F15 - Catálogo de productos
    @GetMapping("/catalogo")
    public String catalogo(Model model) {
        model.addAttribute("productos", productoService.listarDisponibles());
        return "cliente/catalogo";
    }

    // I16 / F16 - Realizar pedido (paso 1: resumen con lo elegido en el catálogo)
    @PostMapping("/pedido/resumen")
    public String resumen(@RequestParam(value = "idProducto", required = false) List<Integer> idsProducto,
                          @RequestParam(value = "cantidad", required = false) List<Integer> cantidades,
                          Model model) {
        List<DetallePedido> detalles = pedidoService.armarDetalle(idsProducto, cantidades);

        if (detalles.isEmpty()) {
            model.addAttribute("error", "Elige al menos un producto con cantidad mayor a 0.");
            model.addAttribute("productos", productoService.listarDisponibles());
            return "cliente/catalogo";
        }

        cargarResumen(model, detalles);
        return "cliente/resumen";
    }

    // I16 / F16 - Realizar pedido (paso 2: datos del cliente + Yape, se registra)
    @PostMapping("/pedido/registrar")
    public String registrar(@ModelAttribute Pedido pedido,
                            @RequestParam(value = "idProducto", required = false) List<Integer> idsProducto,
                            @RequestParam(value = "cantidad", required = false) List<Integer> cantidades,
                            Model model) {
        List<DetallePedido> detalles = pedidoService.armarDetalle(idsProducto, cantidades);
        if (detalles.isEmpty()) {
            return "redirect:/catalogo";
        }

        String error = pedidoService.validarDatosCliente(pedido);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("pedido", pedido);
            cargarResumen(model, detalles);
            return "cliente/resumen";
        }

        int codigo = pedidoService.registrarPedido(pedido, detalles);
        return "redirect:/pedido/seguimiento?codigo=" + codigo
                + "&telefono=" + pedido.getClienteTelefono() + "&nuevo=true";
    }

    // I17 / F17 - Seguimiento del pedido
    @GetMapping("/pedido/seguimiento")
    public String seguimiento(@RequestParam(value = "codigo", required = false) Integer codigo,
                              @RequestParam(value = "telefono", required = false) String telefono,
                              @RequestParam(value = "nuevo", required = false) Boolean nuevo,
                              Model model) {
        if (codigo != null && telefono != null && !telefono.isBlank()) {
            Pedido pedido = pedidoService.buscarParaSeguimiento(codigo, telefono);
            if (pedido == null) {
                model.addAttribute("error", "No encontramos un pedido con ese código y celular. Revisa los datos.");
            } else {
                model.addAttribute("pedido", pedido);
                model.addAttribute("nuevo", nuevo != null && nuevo);
            }
        }
        model.addAttribute("codigo", codigo);
        model.addAttribute("telefono", telefono);
        return "cliente/seguimiento";
    }

    private void cargarResumen(Model model, List<DetallePedido> detalles) {
        model.addAttribute("detalles", detalles);
        model.addAttribute("total", pedidoService.calcularTotal(detalles));
        model.addAttribute("numeroYape", NUMERO_YAPE);
    }
}
