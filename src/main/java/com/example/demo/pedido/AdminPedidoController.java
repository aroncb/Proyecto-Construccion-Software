package com.example.demo.pedido;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.usuario.UsuarioService;

// Controller: interfaces del administrador para gestionar pedidos
@Controller
@RequestMapping("/admin/pedido")
public class AdminPedidoController {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;

    public AdminPedidoController(PedidoService pedidoService, UsuarioService usuarioService) {
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
    }

    // I18 / F18 - Gestión de pedidos (lista con filtro por estado)
    @GetMapping("/list")
    public String listar(@RequestParam(value = "estado", required = false) String estado, Model model) {
        model.addAttribute("pedidos", pedidoService.listar(estado));
        model.addAttribute("estadoActual", estado == null ? "" : estado);
        return "admin-pedido/lista";
    }

    // I18 / F18 - Gestión de pedidos (detalle: productos, cliente y comprobante Yape)
    @GetMapping("/ver")
    public String ver(@RequestParam("id") int id, Model model) {
        model.addAttribute("pedido", pedidoService.obtenerConDetalle(id));
        return "admin-pedido/ver";
    }

    // I19 / F19 - Asignar pedido (formulario)
    @GetMapping("/asignar")
    public String asignar(@RequestParam("id") int id, Model model) {
        cargarFormulario(id, model);
        return "admin-pedido/asignar";
    }

    // I19 / F19 - Asignar pedido (guarda)
    @PostMapping("/asignar")
    public String guardarAsignacion(@RequestParam("id") int id,
                                    @RequestParam(value = "idMotorizado", required = false) Integer idMotorizado,
                                    Model model) {
        String error = pedidoService.asignarMotorizado(id, idMotorizado);
        if (error != null) {
            model.addAttribute("error", error);
            cargarFormulario(id, model);
            return "admin-pedido/asignar";
        }
        return "redirect:/admin/pedido/list";
    }

    // I20 / F20 - Reasignar pedido (formulario)
    @GetMapping("/reasignar")
    public String reasignar(@RequestParam("id") int id, Model model) {
        cargarFormulario(id, model);
        return "admin-pedido/reasignar";
    }

    // I20 / F20 - Reasignar pedido (guarda)
    @PostMapping("/reasignar")
    public String guardarReasignacion(@RequestParam("id") int id,
                                      @RequestParam(value = "idMotorizado", required = false) Integer idMotorizado,
                                      Model model) {
        String error = pedidoService.reasignarMotorizado(id, idMotorizado);
        if (error != null) {
            model.addAttribute("error", error);
            cargarFormulario(id, model);
            return "admin-pedido/reasignar";
        }
        return "redirect:/admin/pedido/list";
    }

    private void cargarFormulario(int idPedido, Model model) {
        model.addAttribute("pedido", pedidoService.obtenerConDetalle(idPedido));
        model.addAttribute("motorizados", usuarioService.listarMotorizadosActivos());
    }
}
