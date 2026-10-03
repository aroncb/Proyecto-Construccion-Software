package com.example.demo.motorizado;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.login.Sesion;
import com.example.demo.pedido.PedidoService;
import com.example.demo.usuario.Usuario;

// Controller: interfaces del motorizado (usa el usuario de la sesión)
@Controller
@RequestMapping("/motorizado")
public class MotorizadoController {

    private final PedidoService pedidoService;

    public MotorizadoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // I21 / F21 - Pedidos asignados
    @GetMapping("/pedidos")
    public String pedidos(HttpSession session, Model model) {
        int idMotorizado = idMotorizado(session);
        model.addAttribute("pedidos", pedidoService.listarActivosDeMotorizado(idMotorizado));
        model.addAttribute("entregados", pedidoService.listarEntregadosDeMotorizado(idMotorizado));
        return "motorizado/pedidos";
    }

    // I22 / F22 - Aceptar pedido (confirmación)
    @GetMapping("/aceptar")
    public String aceptar(@RequestParam("id") int id, HttpSession session, Model model) {
        model.addAttribute("pedido", pedidoService.obtenerParaMotorizado(id, idMotorizado(session)));
        return "motorizado/aceptar";
    }

    // I22 / F22 - Aceptar pedido
    @PostMapping("/aceptar")
    public String guardarAceptar(@RequestParam("id") int id, HttpSession session, Model model) {
        String error = pedidoService.aceptarPedido(id, idMotorizado(session));
        return responder(error, id, session, model, "motorizado/aceptar");
    }

    // I23 / F23 - Rechazar pedido (confirmación)
    @GetMapping("/rechazar")
    public String rechazar(@RequestParam("id") int id, HttpSession session, Model model) {
        model.addAttribute("pedido", pedidoService.obtenerParaMotorizado(id, idMotorizado(session)));
        return "motorizado/rechazar";
    }

    // I23 / F23 - Rechazar pedido
    @PostMapping("/rechazar")
    public String guardarRechazar(@RequestParam("id") int id, HttpSession session, Model model) {
        String error = pedidoService.rechazarPedido(id, idMotorizado(session));
        return responder(error, id, session, model, "motorizado/rechazar");
    }

    // I24 / F24 - Confirmar entrega (confirmación)
    @GetMapping("/entregar")
    public String entregar(@RequestParam("id") int id, HttpSession session, Model model) {
        model.addAttribute("pedido", pedidoService.obtenerParaMotorizado(id, idMotorizado(session)));
        return "motorizado/entregar";
    }

    // I24 / F24 - Confirmar entrega
    @PostMapping("/entregar")
    public String guardarEntregar(@RequestParam("id") int id, HttpSession session, Model model) {
        String error = pedidoService.confirmarEntrega(id, idMotorizado(session));
        return responder(error, id, session, model, "motorizado/entregar");
    }

    // Si hubo error, vuelve a la misma pantalla con el mensaje; si no, a la lista
    private String responder(String error, int idPedido, HttpSession session, Model model, String vista) {
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("pedido", pedidoService.obtenerParaMotorizado(idPedido, idMotorizado(session)));
            return vista;
        }
        return "redirect:/motorizado/pedidos";
    }

    private int idMotorizado(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute(Sesion.USUARIO);
        return usuario.getId();
    }
}
